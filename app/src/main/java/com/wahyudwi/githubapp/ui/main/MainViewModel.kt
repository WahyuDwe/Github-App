package com.wahyudwi.githubapp.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.UserRepository

class MainViewModel(application: Application) : ViewModel() {
    private val repository = UserRepository(application)

    fun getSearchUser(query: String) = repository.searchUser(query)
}