package com.wahyudwi.githubapp.ui.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.wahyudwi.githubapp.data.UserRepository
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : ViewModel() {
    private val repository = UserRepository(application)

    fun getThemeSettings() = repository.getTheme().asLiveData()

    fun saveThemeSettings(isDarkMode: Boolean) {
        viewModelScope.launch {
            repository.saveTheme(isDarkMode)
        }
    }
}