package com.wahyudwi.githubapp.ui.following

import android.app.Application
import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.UserRepository

class FollowingViewModel(application: Application) : ViewModel() {
    private val repository = UserRepository(application)

    fun getListFollowing(username: String) = repository.listFollowing(username)
}