package com.wahyudwi.githubapp.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.wahyudwi.githubapp.databinding.ActivitySettingBinding
import com.wahyudwi.githubapp.utils.ViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this as AppCompatActivity)
        title = "Setting"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        darkMode()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun obtainViewModel(activity: AppCompatActivity): SettingsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SettingsViewModel::class.java]
    }

    private fun darkMode() {
        binding.switchTheme.apply {
            viewModel.getThemeSettings().observe(this@SettingActivity) {
                if(it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    this.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    this.isChecked = false
                }

                setOnCheckedChangeListener { _, isCheck: Boolean ->
                    viewModel.saveThemeSettings(isCheck)
                }
            }
        }
    }
}