package com.wahyudwi.githubapp.ui.followers

import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.UserRepository

class FollowersViewModel : ViewModel() {
    private val repository = UserRepository()

    fun getListFollowers(username: String) = repository.listFollowers(username)
}