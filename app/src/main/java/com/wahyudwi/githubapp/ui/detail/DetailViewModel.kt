package com.wahyudwi.githubapp.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wahyudwi.githubapp.data.UserRepository

class DetailViewModel(application: Application) : ViewModel() {
    private val repository = UserRepository(application)

    fun getDetailUser(username: String) = repository.detailUser(username)

    fun addToFavorite(id: Int, username: String, avatarUrl: String) =
        repository.addFavorite(id, username, avatarUrl)

    fun checkExistUser(id: Int) = repository.checkUser(id)

    fun removeFromFavorite(id: Int) = repository.removeFavorite(id)

    fun getThemeSettings() = repository.getTheme().asLiveData()
}