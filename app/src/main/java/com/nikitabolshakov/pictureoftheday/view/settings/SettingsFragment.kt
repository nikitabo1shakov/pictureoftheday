package com.nikitabolshakov.pictureoftheday.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.FragmentSettingsBinding
import com.nikitabolshakov.pictureoftheday.model.utils.hide
import com.nikitabolshakov.pictureoftheday.model.utils.show

class SettingsFragment : Fragment() {

    var showThemes = false

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            settingsThemePink.setOnClickListener {
                requireActivity().setTheme(R.style.MyAppTheme_PinkTheme)
                requireActivity().recreate()
            }
            settingsThemeYellow.setOnClickListener {
                requireActivity().setTheme(R.style.MyAppTheme_YellowTheme)
                requireActivity().recreate()
            }
            settingsThemeGreen.setOnClickListener {
                requireActivity().setTheme(R.style.MyAppTheme_GreenTheme)
                requireActivity().recreate()
            }
            settingsMagicButton.setOnClickListener {
                if (showThemes) {
                    themesGroup.show()
                } else {
                    themesGroup.hide()
                }
                showThemes = !showThemes
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}