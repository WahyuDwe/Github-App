package com.wahyudwi.githubapp.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val listUser = MutableLiveData<ArrayList<SearchUser>>()

    fun setListFollowing(username: String): LiveData<ArrayList<SearchUser>> {
        ApiConfig.getService()
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<SearchUser>> {
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
                    Log.d("onFailure", t.message!!)
                }
            })
        return listUser
    }
}