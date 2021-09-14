package com.nikitabolshakov.pictureoftheday.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var themePink = false
    private var themeYellow = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        themePink = savedInstanceState?.getBoolean("themePink") ?: false
        themeYellow = savedInstanceState?.getBoolean("themeYellow") ?: false

        // Выбирается только последнее состояние. ???

        setTheme()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, PODFragment.newInstance())
                .commitNow()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("themePink", themePink)
        outState.putBoolean("themeYellow", themeYellow)
    }

    private fun setTheme() {
        if (themePink) {
            setTheme(R.style.MyAppTheme_PinkTheme)
        }
        if (themeYellow) {
            setTheme(R.style.MyAppTheme_YellowTheme)
        } else {
            setTheme(R.style.Theme_PictureOfTheDay)
        }
        themePink = !themePink
        themeYellow = !themeYellow
    }
}