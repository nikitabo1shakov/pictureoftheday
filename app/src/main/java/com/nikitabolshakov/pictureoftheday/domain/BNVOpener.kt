package com.nikitabolshakov.pictureoftheday.domain

import androidx.fragment.app.FragmentManager
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.presentation.view.api.viewpager.ViewPagerFragment
import com.nikitabolshakov.pictureoftheday.presentation.view.demo.DemoFragment
import com.nikitabolshakov.pictureoftheday.presentation.view.home.HomeFragment
import com.nikitabolshakov.pictureoftheday.presentation.view.settings.SettingsFragment

class BNVOpener(private val fragmentManager: FragmentManager) {

    fun openHomeFragmentNow() {
        fragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, HomeFragment())
            .commitNow()
    }

    fun openHomeFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, HomeFragment())
            .commitAllowingStateLoss()
    }

    fun openViewPagerFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, ViewPagerFragment())
            .commitAllowingStateLoss()
    }

    fun openDemoFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, DemoFragment())
            .commitAllowingStateLoss()
    }

    fun openSettingsFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, SettingsFragment())
            .commitAllowingStateLoss()
    }
}