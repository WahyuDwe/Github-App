package com.wahyudwi.githubapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wahyudwi.githubapp.data.model.DetailUserResponse
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.data.model.UserResponse
import com.wahyudwi.githubapp.network.ApiConfig
import com.wahyudwi.githubapp.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository {
    private val retrofit: ApiService = ApiConfig.getService()

    fun searchUser(query: String): LiveData<ArrayList<SearchUser>> {
        val listUser = MutableLiveData<ArrayList<SearchUser>>()

        retrofit.getSearchUser(query).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body()?.items)
                }

                val message = when (response.code()) {
                    401 -> "${response.code()} : Forbidden"
                    403 -> "${response.code()} : Bad Request"
                    404 -> "${response.code()} : Not Found"
                    else -> "${response.code()} : ${response.body()}"
                }
                Log.d("onResponseSearch", message)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("onFailureSearch", t.message!!)
            }

        })

        return listUser
    }

    fun detailUser(username: String): LiveData<DetailUserResponse> {
        val detail = MutableLiveData<DetailUserResponse>()

        retrofit.getDetailUser(username).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    detail.postValue(response.body())
                }

                val message = when (response.code()) {
                    401 -> "${response.code()} : Forbidden"
                    403 -> "${response.code()} : Bad Request"
                    404 -> "${response.code()} : Not Found"
                    else -> "${response.code()} : ${response.body()}"
                }

                Log.d("onResponseDetail", message)
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("onFailureDetail", t.message!!)
            }

        })

        return detail
    }

    fun listFollowers(username: String): LiveData<ArrayList<SearchUser>> {
        val listUser = MutableLiveData<ArrayList<SearchUser>>()

        retrofit.getFollowers(username).enqueue(object : Callback<ArrayList<SearchUser>> {
            override fun onResponse(
                call: Call<ArrayList<SearchUser>>,
                response: Response<ArrayList<SearchUser>>
            ) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body())
                }

                val message = when (response.code()) {
                    401 -> "${response.code()} : Forbidden"
                    403 -> "${response.code()} : Bad Request"
                    404 -> "${response.code()} : Not Found"
                    else -> "${response.code()} : ${response.body()}"
                }

                Log.d("onResponseFollowers", message)
            }

            override fun onFailure(call: Call<ArrayList<SearchUser>>, t: Throwable) {
                Log.d("onFailureFollowers", t.message!!)
            }

        })

        return listUser
    }

    fun listFollowing(username: String): LiveData<ArrayList<SearchUser>> {
        val listUser = MutableLiveData<ArrayList<SearchUser>>()

        retrofit.getFollowing(username).enqueue(object : Callback<ArrayList<SearchUser>> {
            override fun onResponse(
                call: Call<ArrayList<SearchUser>>,
                response: Response<ArrayList<SearchUser>>
            ) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body())
                }

                val message = when (response.code()) {
                    401 -> "${response.code()} : Forbidden"
                    403 -> "${response.code()} : Bad Request"
                    404 -> "${response.code()} : Not Found"
                    else -> "${response.code()} : ${response.body()}"
                }

                Log.d("onResponseFollowing", message)
            }

            override fun onFailure(call: Call<ArrayList<SearchUser>>, t: Throwable) {
                Log.d("onFailureFollowing", t.message!!)
            }

        })

        return listUser
    }
}