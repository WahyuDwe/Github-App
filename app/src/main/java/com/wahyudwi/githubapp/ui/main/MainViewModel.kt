package com.wahyudwi.githubapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.wahyudwi.githubapp.data.UserRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    fun getSearchUser(query: String) = repository.searchUser(query)
}