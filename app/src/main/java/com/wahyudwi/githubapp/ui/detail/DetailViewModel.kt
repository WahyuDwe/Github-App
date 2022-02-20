package com.wahyudwi.githubapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.local.entity.FavoriteEntity
import com.wahyudwi.githubapp.data.local.room.FavoriteDao
import com.wahyudwi.githubapp.data.local.room.FavoriteDatabase
import com.wahyudwi.githubapp.data.model.DetailUserResponse
import com.wahyudwi.githubapp.network.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    val detailUser = MutableLiveData<DetailUserResponse>()

    private var favoriteDb: FavoriteDatabase? = FavoriteDatabase.getInstance(application)
    private var favoriteDao: FavoriteDao? = favoriteDb?.favoriteDao()

    fun setUserDetail(username: String) {
        ApiConfig.getService()
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }

                    val message = when (response.code()) {
                        401 -> "${response.code()}: Forbidden"
                        403 -> "${response.code()}: Bad Request"
                        404 -> "${response.code()}: Not Found"
                        else -> "${response.code()}: ${response.body()}"
                    }

                    Log.d("onResponseDetail", message)
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("onFailure", t.message!!)
                }

            })
    }

    fun addToFavorite(id: Int, username: String, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteEntity(
                id,
                username,
                avatarUrl
            )
            favoriteDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = favoriteDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.deleteFavorite(id)
        }
    }
}