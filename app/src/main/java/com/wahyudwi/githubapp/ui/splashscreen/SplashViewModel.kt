package com.wahyudwi.githubapp.ui.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.wahyudwi.githubapp.data.UserRepository

class SplashViewModel(application: Application): AndroidViewModel(application) {
    private val repository = UserRepository(application)

    fun getThemeSetting() = repository.getTheme().asLiveData()
}