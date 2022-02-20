package com.wahyudwi.githubapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.local.entity.FavoriteEntity
import com.wahyudwi.githubapp.data.local.room.FavoriteDao
import com.wahyudwi.githubapp.data.local.room.FavoriteDatabase

class FavoriteViewModel(application: Application) : ViewModel() {
    private var favoriteDb: FavoriteDatabase? = FavoriteDatabase.getInstance(application)
    private var favoriteDao: FavoriteDao? = favoriteDb?.favoriteDao()

    fun getFavoritedUser(): LiveData<List<FavoriteEntity>>? = favoriteDao?.getFavoritedUser()
}