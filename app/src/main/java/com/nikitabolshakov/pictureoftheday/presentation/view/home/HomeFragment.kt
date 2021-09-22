package com.nikitabolshakov.pictureoftheday.presentation.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.api.load
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.FragmentHomeBinding
import com.nikitabolshakov.pictureoftheday.presentation.viewmodel.apod.APODState
import com.nikitabolshakov.pictureoftheday.presentation.viewmodel.apod.APODViewModel
import com.nikitabolshakov.pictureoftheday.utils.hide
import com.nikitabolshakov.pictureoftheday.utils.show
import com.nikitabolshakov.pictureoftheday.utils.toast
import java.util.*

class HomeFragment : Fragment() {

    private var hideShowHomeFragmentGroup = true
    private var hideShowApodText = true

    private val viewModel: APODViewModel by lazy {
        ViewModelProvider(this).get(APODViewModel::class.java)
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDataToday().observe(viewLifecycleOwner) { renderData(it) }

        with(binding) {
            magicButton.setOnClickListener {
                hideShowHomeFragmentGroup = !hideShowHomeFragmentGroup
                homeFragmentGroup.visibility =
                    if (hideShowHomeFragmentGroup) View.VISIBLE else View.GONE
            }

            textInputLayout.setEndIconOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("https://en.wikipedia.org/wiki/${binding.textInputEditText.text.toString()}")
                })
            }

            chipHideShowApodText.setOnClickListener {
                val transition = ChangeBounds()
                transition.duration = 1000
                TransitionManager.beginDelayedTransition(homeFragment, transition)
                hideShowApodText = !hideShowApodText
                apodTextView.visibility = if (hideShowApodText) View.VISIBLE else View.GONE
            }

            chipApodImageToday.setOnClickListener {
                viewModel.getDataToday().observe(viewLifecycleOwner) { renderData(it) }
            }

            chipApodImageYesterday.setOnClickListener {
                viewModel.getDataYesterday().observe(viewLifecycleOwner) { renderData(it) }
            }

            chipApodImageDayBeforeYesterday.setOnClickListener {
                viewModel.getDataDayBeforeYesterday().observe(viewLifecycleOwner) { renderData(it) }
            }
        }
    }

    private fun renderData(state: APODState) {
        when (state) {
            is APODState.Success -> {
                binding.homeFragment.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                val serverResponseData = state.serverResponseData
                val url = serverResponseData.url
                val copyright = serverResponseData.copyright
                if (url.isNullOrEmpty()) {
                    toast("Image Link is Empty")
                } else {
                    binding.apodImageView.load(url) {
                        lifecycle(this@HomeFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
                if (copyright.isNullOrEmpty()) {
                    toast("Copyright Link is Empty")
                    binding.apodTextView.text = getString(R.string.apod_default_copyright)
                } else {
                    binding.apodTextView.text = copyright
                }
            }
            is APODState.Loading -> {
                binding.homeFragment.hide()
                binding.includedLoadingLayout.loadingLayout.show()
            }
            is APODState.Error -> {
                binding.homeFragment.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                toast("Error")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}