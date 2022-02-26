package com.wahyudwi.githubapp.ui.splashscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wahyudwi.githubapp.data.UserRepository

class SplashViewModel(application: Application): ViewModel() {
    private val repository = UserRepository(application)

    fun getThemeSetting() = repository.getTheme().asLiveData()
}