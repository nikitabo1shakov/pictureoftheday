package com.nikitabolshakov.pictureoftheday.view.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.behavior.SwipeDismissBehavior
import com.nikitabolshakov.pictureoftheday.databinding.FragmentDemoBinding
import com.nikitabolshakov.pictureoftheday.model.utils.hide
import com.nikitabolshakov.pictureoftheday.model.utils.toast

class DemoFragment : Fragment() {

    private var _binding: FragmentDemoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipe: SwipeDismissBehavior<CardView?> = SwipeDismissBehavior()

        swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY)
        swipe.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View?) {
                toast("Card View Swiped")
                binding.cardViewToSwipe.hide()
            }

            override fun onDragStateChanged(state: Int) {}
        }
        val coordinatorParams =
            binding.cardViewToSwipe.layoutParams as CoordinatorLayout.LayoutParams
        coordinatorParams.behavior = swipe
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}