package com.wahyudwi.githubapp.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wahyudwi.githubapp.ui.detail.DetailViewModel
import com.wahyudwi.githubapp.ui.favorite.FavoriteViewModel
import com.wahyudwi.githubapp.ui.followers.FollowersViewModel
import com.wahyudwi.githubapp.ui.following.FollowingViewModel
import com.wahyudwi.githubapp.ui.main.MainViewModel
import com.wahyudwi.githubapp.ui.settings.SettingsViewModel

class ViewModelFactory constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application) as T

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(application) as T

            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> FollowersViewModel() as T

            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> FollowingViewModel() as T

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel(application) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(application)
            }
    }
}