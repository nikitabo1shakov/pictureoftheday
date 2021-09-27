package com.nikitabolshakov.pictureoftheday.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.ActivityMainBinding
import com.nikitabolshakov.pictureoftheday.view.api.viewpager.ViewPagerFragment
import com.nikitabolshakov.pictureoftheday.view.home.HomeFragment
import com.nikitabolshakov.pictureoftheday.view.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.mainActivityContainer.id, HomeFragment())
                .commitNow()
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnv_home -> {
                    openHomeFragment()
                    true
                }
                R.id.bnv_api -> {
                    openViewPagerFragment()
                    true
                }
                R.id.bnv_settings -> {
                    openSettingsFragment()
                    true
                }
                else -> {
                    openHomeFragment()
                    true
                }
            }
        }

        binding.bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bnv_home -> {
                    openHomeFragment()
                }
                R.id.bnv_api -> {
                    openViewPagerFragment()
                }
                R.id.bnv_settings -> {
                    openSettingsFragment()
                }
            }
        }
    }

    private fun openHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainActivityContainer.id, HomeFragment())
            .commitAllowingStateLoss()
    }

    private fun openViewPagerFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainActivityContainer.id, ViewPagerFragment())
            .commitAllowingStateLoss()
    }

    private fun openSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainActivityContainer.id, SettingsFragment())
            .commitAllowingStateLoss()
    }
}