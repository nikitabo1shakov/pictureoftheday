package com.nikitabolshakov.pictureoftheday.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.ActivityMainBinding
import com.nikitabolshakov.pictureoftheday.domain.BNVOpener

class MainActivity : AppCompatActivity() {

    private val bnvOpener: BNVOpener = BNVOpener(supportFragmentManager)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            bnvOpener.openHomeFragmentNow()
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnv_home -> {
                    bnvOpener.openHomeFragment()
                    true
                }
                R.id.bnv_api -> {
                    bnvOpener.openViewPagerFragment()
                    true
                }
                R.id.bnv_demo -> {
                    bnvOpener.openDemoFragment()
                    true
                }
                R.id.bnv_settings -> {
                    bnvOpener.openSettingsFragment()
                    true
                }
                else -> {
                    bnvOpener.openHomeFragment()
                    true
                }
            }
        }

        binding.bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bnv_home -> {
                    bnvOpener.openHomeFragment()
                }
                R.id.bnv_api -> {
                    bnvOpener.openViewPagerFragment()
                }
                R.id.bnv_demo -> {
                    bnvOpener.openDemoFragment()
                }
                R.id.bnv_settings -> {
                    bnvOpener.openSettingsFragment()
                }
            }
        }
    }
}