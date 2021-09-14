package com.nikitabolshakov.pictureoftheday.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.FragmentMainBinding
import com.nikitabolshakov.pictureoftheday.model.utils.hide
import com.nikitabolshakov.pictureoftheday.model.utils.show
import com.nikitabolshakov.pictureoftheday.model.utils.toast
import com.nikitabolshakov.pictureoftheday.viewmodel.PODState
import com.nikitabolshakov.pictureoftheday.viewmodel.PODViewModel

class MainFragment : Fragment() {

    private val viewModel: PODViewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<PODState> { renderData(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)

        binding.textInputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.textInputEditText.text.toString()}")
            })
        }
    }

    private fun renderData(state: PODState) {
        when (state) {
            is PODState.Success -> {
                binding.mainFragment.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                val serverResponseData = state.serverResponseData
                val hdUrl = serverResponseData.hdurl
                val copyright = serverResponseData.copyright
                if (hdUrl.isNullOrEmpty()) {
                    toast("Image Link is Empty")
                } else {
                    binding.imageViewOfTheDay.load(hdUrl) {
                        lifecycle(this@MainFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
                if (copyright.isNullOrEmpty()) {
                    toast("Copyright Link is Empty")
                } else {
                    binding.textViewOfTheDay.text = copyright
                }
            }
            is PODState.Loading -> {
                binding.mainFragment.hide()
                binding.includedLoadingLayout.loadingLayout.show()
            }
            is PODState.Error -> {
                binding.mainFragment.show()
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