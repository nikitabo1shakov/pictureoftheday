package com.nikitabolshakov.pictureoftheday.presentation.view.home

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
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
    private var isExpanded = false

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

    @RequiresApi(Build.VERSION_CODES.O)
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
                apodTitleTextView.visibility = if (hideShowApodText) View.VISIBLE else View.GONE
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

            apodImageView.setOnClickListener {
                isExpanded = !isExpanded
                TransitionManager.beginDelayedTransition(
                    homeFragment, TransitionSet()
                        .addTransition(ChangeBounds())
                        .addTransition(ChangeImageTransform())
                )

                val params: ViewGroup.LayoutParams = apodImageView.layoutParams
                params.height =
                    if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
                apodImageView.layoutParams = params
                apodImageView.scaleType =
                    if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
            }
        }

        activity?.let {
            binding.apodCopyrightTextView.typeface =
                Typeface.createFromAsset(it.assets, "SpaceQuest-Xj4o.ttf")
        }
    }

    private fun renderData(state: APODState) {
        when (state) {
            is APODState.Success -> {
                binding.homeFragment.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                val serverResponseData = state.serverResponseData
                val url = serverResponseData.url
                val title = serverResponseData.title
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
                if (title.isNullOrEmpty()) {
                    toast("Title Link is Empty")
                    binding.apodTitleTextView.text = getString(R.string.apod_default_title)
                } else {
                    binding.apodTitleTextView.text = title
                }
                if (copyright.isNullOrEmpty()) {
                    toast("Copyright Link is Empty")
                    binding.apodCopyrightTextView.text = getString(R.string.apod_default_copyright)
                } else {
                    binding.apodCopyrightTextView.text = title
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