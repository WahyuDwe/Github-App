package com.wahyudwi.githubapp.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wahyudwi.githubapp.data.local.room.FavoriteDao
import com.wahyudwi.githubapp.data.local.room.FavoriteDatabase
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.data.model.UserResponse
import com.wahyudwi.githubapp.network.ApiConfig
import com.wahyudwi.githubapp.network.ApiService
import com.wahyudwi.githubapp.utils.ThemePreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository(application: Application) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val pref: ThemePreference = ThemePreference.getInstance(application.dataStore)
    private val retrofit: ApiService = ApiConfig.getService()
    private val dao: FavoriteDao

    init {
        
        val db: FavoriteDatabase = FavoriteDatabase.getInstance(application)
        dao = db.favoriteDao()
    }

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

    fun getTheme() = pref.getThemeSetting()
}