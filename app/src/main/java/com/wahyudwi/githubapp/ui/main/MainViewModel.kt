package com.wahyudwi.githubapp.ui.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.data.model.UserResponse
import com.wahyudwi.githubapp.network.ApiConfig
import com.wahyudwi.githubapp.utils.ThemePreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<SearchUser>>()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val pref: ThemePreference = ThemePreference.getInstance(application.dataStore)

    fun setSearchUser(query: String): LiveData<ArrayList<SearchUser>> {
        ApiConfig.getService()
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }

                    val message = when (response.code()) {
                        401 -> "${response.code()} : Forbidden"
                        403 -> "${response.code()} : Bad Request"
                        404 -> "${response.code()} : Not Found"
                        else -> "${response.code()} : ${response.body()}"
                    }
                    Log.d("onResponse", message)
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("onFailure", t.message!!)

                }
            })
        return listUsers
    }

    fun getThemeSettings(): LiveData<Boolean> = pref.getThemeSetting().asLiveData()
}