package com.wahyudwi.githubapp.ui.main

import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.UserRepository

class MainViewModel() : ViewModel() {
    private val repository = UserRepository()

    fun getSearchUser(query: String) = repository.searchUser(query)
}