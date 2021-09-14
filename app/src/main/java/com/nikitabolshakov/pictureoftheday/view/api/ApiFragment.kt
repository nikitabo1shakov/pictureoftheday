package com.nikitabolshakov.pictureoftheday.view.api

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.FragmentApiBinding

private const val EARTH = 0
private const val MARS = 1
private const val WEATHER = 2

class ApiFragment : Fragment() {

    private var _binding: FragmentApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            apiViewPager.adapter = ViewPagerAdapter(childFragmentManager)
            apiTabLayout.setupWithViewPager(apiViewPager)

            setHighlightedTab(EARTH)

            apiViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageSelected(position: Int) {
                    setHighlightedTab(position)
                }

                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }
            })
        }
    }

    private fun setHighlightedTab(position: Int) {

        val layoutInflater = LayoutInflater.from(requireContext())

        with(binding) {
            apiTabLayout.getTabAt(EARTH)?.customView = null
            apiTabLayout.getTabAt(MARS)?.customView = null
            apiTabLayout.getTabAt(WEATHER)?.customView = null
        }

        when (position) {
            EARTH -> {
                setEarthTabHighlighted(layoutInflater)
            }
            MARS -> {
                setMarsTabHighlighted(layoutInflater)
            }
            WEATHER -> {
                setWeatherTabHighlighted(layoutInflater)
            }
            else -> {
                setEarthTabHighlighted(layoutInflater)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun setEarthTabHighlighted(layoutInflater: LayoutInflater) {
        val earth =
            layoutInflater.inflate(R.layout.fragment_api_custom_tab_earth, null)
        earth.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent
                )
            )
        with(binding) {
            apiTabLayout.getTabAt(EARTH)?.customView = earth
            apiTabLayout.getTabAt(MARS)?.customView =
                layoutInflater.inflate(R.layout.fragment_api_custom_tab_mars, null)
            apiTabLayout.getTabAt(WEATHER)?.customView =
                layoutInflater.inflate(R.layout.fragment_api_custom_tab_weather, null)
        }
    }

    @SuppressLint("InflateParams")
    private fun setMarsTabHighlighted(layoutInflater: LayoutInflater) {
        val mars =
            layoutInflater.inflate(R.layout.fragment_api_custom_tab_mars, null)
        mars.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent
                )
            )
        with(binding) {
            apiTabLayout.getTabAt(EARTH)?.customView =
                layoutInflater.inflate(R.layout.fragment_api_custom_tab_earth, null)
            apiTabLayout.getTabAt(MARS)?.customView = mars
            apiTabLayout.getTabAt(WEATHER)?.customView =
                layoutInflater.inflate(R.layout.fragment_api_custom_tab_weather, null)
        }
    }

    @SuppressLint("InflateParams")
    private fun setWeatherTabHighlighted(layoutInflater: LayoutInflater) {
        val weather =
            layoutInflater.inflate(R.layout.fragment_api_custom_tab_weather, null)
        weather.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent
                )
            )
        with(binding) {
            apiTabLayout.getTabAt(EARTH)?.customView =
                layoutInflater.inflate(R.layout.fragment_api_custom_tab_earth, null)
            apiTabLayout.getTabAt(MARS)?.customView =
                layoutInflater.inflate(R.layout.fragment_api_custom_tab_mars, null)
            apiTabLayout.getTabAt(WEATHER)?.customView = weather
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}