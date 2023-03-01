package com.wahyudwi.githubapp.ui.settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.wahyudwi.githubapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        darkMode()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun darkMode() {
        binding.switchTheme.apply {
            viewModel.getThemeSettings().observe(this@SettingActivity) {
                if (it) {
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