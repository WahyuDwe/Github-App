package com.wahyudwi.githubapp.ui.following

import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.UserRepository

class FollowingViewModel : ViewModel() {
    private val repository = UserRepository()

    fun getListFollowing(username: String) = repository.listFollowing(username)
}