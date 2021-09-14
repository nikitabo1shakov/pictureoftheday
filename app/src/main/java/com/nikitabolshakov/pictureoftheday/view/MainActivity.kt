package com.nikitabolshakov.pictureoftheday.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.ActivityMainBinding
import com.nikitabolshakov.pictureoftheday.view.api.ApiFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.mainActivityContainer.id, MainFragment())
                .commitNow()
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnv_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainActivityContainer.id, MainFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bnv_api -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainActivityContainer.id, ApiFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bnv_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainActivityContainer.id, SettingsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.bnv_home

        binding.bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bnv_home -> {
                    //Item tapped
                }
                R.id.bnv_api -> {
                    //Item tapped
                }
                R.id.bnv_settings -> {
                    //Item tapped
                }
            }
        }
    }
}