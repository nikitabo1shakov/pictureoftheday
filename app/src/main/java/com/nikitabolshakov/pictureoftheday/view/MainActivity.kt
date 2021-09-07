package com.nikitabolshakov.pictureoftheday.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikitabolshakov.pictureoftheday.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}