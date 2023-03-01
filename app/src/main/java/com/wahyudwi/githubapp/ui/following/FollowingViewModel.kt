package com.wahyudwi.githubapp.ui.following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.wahyudwi.githubapp.data.UserRepository

class FollowingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    fun getListFollowing(username: String) = repository.listFollowing(username)
}