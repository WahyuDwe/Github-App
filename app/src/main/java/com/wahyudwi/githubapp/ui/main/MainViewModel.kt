package com.wahyudwi.githubapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wahyudwi.githubapp.data.UserRepository
import com.wahyudwi.githubapp.data.model.SearchUser
import kotlinx.coroutines.Dispatchers

class MainViewModel(application: Application) : ViewModel() {
    private val repository = UserRepository(application)

    fun searchUser(query: String): LiveData<ArrayList<SearchUser>> = repository.searchUser(query)

    fun getThemeSettings() = repository.getTheme().asLiveData(Dispatchers.IO)

}