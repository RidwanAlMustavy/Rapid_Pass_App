package com.example.rapidpass

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingActivity : AppCompatActivity() {

    private lateinit var switchDark: Switch
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)

        // Apply window insets padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize SharedPreferences
        prefs = getSharedPreferences("settings", MODE_PRIVATE)

        // Initialize switch
        switchDark = findViewById(R.id.switch_dark)

        // Load saved preference
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        switchDark.isChecked = isDarkMode
        setDarkMode(isDarkMode)

        // Handle toggle
        switchDark.setOnCheckedChangeListener { _, isChecked ->
            setDarkMode(isChecked)
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
        }
    }

    private fun setDarkMode(enabled: Boolean) {
        if (enabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
