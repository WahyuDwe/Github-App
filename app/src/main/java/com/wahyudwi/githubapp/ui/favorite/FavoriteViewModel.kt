package com.wahyudwi.githubapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.wahyudwi.githubapp.data.UserRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    fun getFavoritedUser() = repository.getFavorite()
}