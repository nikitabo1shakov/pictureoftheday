package com.nikitabolshakov.pictureoftheday.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.FragmentHomeBinding
import com.nikitabolshakov.pictureoftheday.model.utils.hide
import com.nikitabolshakov.pictureoftheday.model.utils.show
import com.nikitabolshakov.pictureoftheday.model.utils.toast
import com.nikitabolshakov.pictureoftheday.viewmodel.apod.APODState
import com.nikitabolshakov.pictureoftheday.viewmodel.apod.APODViewModel

class HomeFragment : Fragment() {

    private var showHomeFragment = false

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

        val observer = Observer<APODState> { renderData(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)

        with(binding) {
            magicButton.setOnClickListener {
                if (showHomeFragment) {
                    homeFragmentGroup.show()
                } else {
                    homeFragmentGroup.hide()
                }
                showHomeFragment = !showHomeFragment
            }

            textInputLayout.setEndIconOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("https://en.wikipedia.org/wiki/${binding.textInputEditText.text.toString()}")
                })
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