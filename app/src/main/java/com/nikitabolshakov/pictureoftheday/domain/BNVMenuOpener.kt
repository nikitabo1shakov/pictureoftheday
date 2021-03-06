package com.nikitabolshakov.pictureoftheday.domain

import androidx.fragment.app.FragmentManager
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.presentation.view.api.viewpager.ViewPagerFragment
import com.nikitabolshakov.pictureoftheday.presentation.view.home.HomeFragment
import com.nikitabolshakov.pictureoftheday.presentation.view.cases.ListOfCasesFragment
import com.nikitabolshakov.pictureoftheday.presentation.view.settings.SettingsFragment

class BNVMenuOpener(private val fragmentManager: FragmentManager) {

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

    fun openListOfCasesFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, ListOfCasesFragment())
            .commitAllowingStateLoss()
    }

    fun openSettingsFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, SettingsFragment())
            .commitAllowingStateLoss()
    }
}