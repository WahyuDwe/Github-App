package com.wahyudwi.githubapp.ui.detail

import androidx.lifecycle.ViewModel
import com.wahyudwi.githubapp.data.UserRepository

class DetailViewModel : ViewModel() {
    private val repository = UserRepository()

    fun getDetailUser(username: String) = repository.detailUser(username)
}
