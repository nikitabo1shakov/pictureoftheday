package com.nikitabolshakov.pictureoftheday.view.apiviewpager.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.FragmentMarsBinding
import com.nikitabolshakov.pictureoftheday.model.utils.hide
import com.nikitabolshakov.pictureoftheday.model.utils.show
import com.nikitabolshakov.pictureoftheday.model.utils.toast
import com.nikitabolshakov.pictureoftheday.viewmodel.mars.MRFState
import com.nikitabolshakov.pictureoftheday.viewmodel.mars.MRFViewModel

class MarsFragment : Fragment() {

    private val viewModel: MRFViewModel by lazy {
        ViewModelProvider(this).get(MRFViewModel::class.java)
    }

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<MRFState> { renderData(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    private fun renderData(state: MRFState) {
        when (state) {
            is MRFState.Success -> {
                binding.marsFragment.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                val serverResponseData = state.serverResponseData
                val listPhotos = serverResponseData.photos
                val photoUrl = listPhotos[50].img_src
                if (listPhotos.isNullOrEmpty()) {
                    toast("ListPhotos Link is Empty")
                } else {
                    binding.marsImageView.load(photoUrl) {
                        lifecycle(this@MarsFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is MRFState.Loading -> {
                binding.marsFragment.hide()
                binding.includedLoadingLayout.loadingLayout.show()
            }
            is MRFState.Error -> {
                binding.marsFragment.show()
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