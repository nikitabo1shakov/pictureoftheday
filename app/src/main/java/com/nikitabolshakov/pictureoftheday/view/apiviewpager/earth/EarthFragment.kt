package com.nikitabolshakov.pictureoftheday.view.apiviewpager.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.FragmentEarthBinding
import com.nikitabolshakov.pictureoftheday.model.utils.hide
import com.nikitabolshakov.pictureoftheday.model.utils.show
import com.nikitabolshakov.pictureoftheday.model.utils.toast
import com.nikitabolshakov.pictureoftheday.viewmodel.earth.EarthState
import com.nikitabolshakov.pictureoftheday.viewmodel.earth.EarthViewModel

class EarthFragment : Fragment() {

    private val viewModel: EarthViewModel by lazy {
        ViewModelProvider(this).get(EarthViewModel::class.java)
    }

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<EarthState> { renderData(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    private fun renderData(state: EarthState) {
        when (state) {
            is EarthState.Success -> {
                binding.earthFragment.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                val serverResponseData = state.serverResponseData
                val url = serverResponseData.url
                if (url.isEmpty()) {
                    toast("Image Link is Empty")
                } else {
                    binding.earthImageView.load(url) {
                        lifecycle(this@EarthFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is EarthState.Loading -> {
                binding.earthFragment.hide()
                binding.includedLoadingLayout.loadingLayout.show()
            }
            is EarthState.Error -> {
                binding.earthFragment.show()
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