package com.nikitabolshakov.pictureoftheday.presentation.view.api.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.FragmentEarthBinding
import com.nikitabolshakov.pictureoftheday.utils.hide
import com.nikitabolshakov.pictureoftheday.utils.show
import com.nikitabolshakov.pictureoftheday.utils.toast
import com.nikitabolshakov.pictureoftheday.presentation.viewmodel.earth.EarthState
import com.nikitabolshakov.pictureoftheday.presentation.viewmodel.earth.EarthViewModel

class EarthFragment : Fragment() {

    private var isExpanded = false

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

        with(binding) {
            earthImageView.setOnClickListener {
                isExpanded = !isExpanded
                TransitionManager.beginDelayedTransition(
                    earthFragment, TransitionSet()
                        .addTransition(ChangeBounds())
                        .addTransition(ChangeImageTransform())
                )

                val params: ViewGroup.LayoutParams = earthImageView.layoutParams
                params.height =
                    if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.MATCH_PARENT
                earthImageView.layoutParams = params
                earthImageView.scaleType =
                    if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
            }
        }
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