package com.wahyudwi.githubapp.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wahyudwi.githubapp.data.local.entity.FavoriteEntity
import com.wahyudwi.githubapp.data.local.room.FavoriteDao
import com.wahyudwi.githubapp.data.local.room.FavoriteDatabase
import com.wahyudwi.githubapp.data.model.DetailUserResponse
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.data.model.UserResponse
import com.wahyudwi.githubapp.network.ApiConfig
import com.wahyudwi.githubapp.network.ApiService
import com.wahyudwi.githubapp.utils.EspressoIdlingResource
import com.wahyudwi.githubapp.utils.ThemePreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository(application: Application) {
    private val pref: ThemePreference
    private val favoriteDao: FavoriteDao
    private val retrofit: ApiService = ApiConfig.getService()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    init {
        val db: FavoriteDatabase = FavoriteDatabase.getInstance(application)
        favoriteDao = db.favoriteDao()
        pref = ThemePreference.getInstance(application.dataStore)
    }

    fun searchUser(query: String): LiveData<ArrayList<SearchUser>> {
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("onFailureSearch", t.message!!)
                EspressoIdlingResource.decrement()
            }

        })

        return listUser
    }

    fun detailUser(username: String): LiveData<DetailUserResponse> {
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("onFailureDetail", t.message!!)
                EspressoIdlingResource.decrement()
            }

        })

        return detail
    }

    fun listFollowers(username: String): LiveData<ArrayList<SearchUser>> {
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<ArrayList<SearchUser>>, t: Throwable) {
                Log.d("onFailureFollowers", t.message!!)
                EspressoIdlingResource.decrement()
            }

        })

        return listUser
    }

    fun listFollowing(username: String): LiveData<ArrayList<SearchUser>> {
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<ArrayList<SearchUser>>, t: Throwable) {
                Log.d("onFailureFollowing", t.message!!)
                EspressoIdlingResource.decrement()
            }

        })

        return listUser
    }

    fun checkUser(id: Int) = favoriteDao.checkUser(id)

    fun addFavorite(id: Int, username: String, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteEntity(
                id,
                username,
                avatarUrl
            )
            favoriteDao.addToFavorite(user)
        }
    }

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao.deleteFavorite(id)
        }
    }

    suspend fun saveTheme(darkMode: Boolean) = pref.saveThemeSetting(darkMode)

    fun getTheme() = pref.getThemeSetting()
}