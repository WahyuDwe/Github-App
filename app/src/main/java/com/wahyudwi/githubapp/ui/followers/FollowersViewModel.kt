package com.wahyudwi.githubapp.ui.followers

import android.app.Application
import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.UserRepository

class FollowersViewModel(application: Application) : ViewModel() {
    private val repository = UserRepository(application)

    fun getListFollowers(username: String) = repository.listFollowers(username)
}