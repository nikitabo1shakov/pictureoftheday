package com.nikitabolshakov.pictureoftheday.presentation.view.cases

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.data.local.cases.ComplexCase
import com.nikitabolshakov.pictureoftheday.data.local.cases.ItemData
import com.nikitabolshakov.pictureoftheday.data.local.cases.SimpleCase

class ListOfCasesAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    inner class SimpleCaseViewHolder(view: View) : BaseViewHolder(view) {

        private val text = view.findViewById<TextView>(R.id.text_simple_case)

        init {
            view.setOnClickListener {
                deleteCase(layoutPosition)
            }
        }

        fun bind(case: SimpleCase) {
            text.text = case.text
        }
    }

    inner class ComplexCaseViewHolder(view: View) : BaseViewHolder(view) {

        private val heading = view.findViewById<TextView>(R.id.heading_complex_case)
        private val text = view.findViewById<TextView>(R.id.text_complex_case)

        fun bind(case: ComplexCase) {
            heading.text = case.heading
            text.text = case.text
        }
    }

    companion object {
        private const val SIMPLE_CASE_TYPE = 1
        private const val COMPLEX_CASE_TYPE = 2
    }

    var cases = mutableListOf<ItemData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        if (viewType == SIMPLE_CASE_TYPE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_simple_case, parent, false)
            SimpleCaseViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_complex_case, parent, false)
            ComplexCaseViewHolder(view)
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        val currentCases = cases[position]

        if (getItemViewType(position) == SIMPLE_CASE_TYPE) {
            (holder as SimpleCaseViewHolder).bind(currentCases as SimpleCase)
        } else {
            (holder as ComplexCaseViewHolder).bind(currentCases as ComplexCase)
        }
    }

    override fun getItemCount(): Int = cases.size

    override fun getItemViewType(position: Int): Int =
        if (cases[position] is SimpleCase) {
            SIMPLE_CASE_TYPE
        } else {
            COMPLEX_CASE_TYPE
        }

    fun addCase(case: ItemData) {
        cases.add(case)
        notifyItemInserted(cases.size - 1)
    }

    fun deleteCase(position: Int) {
        cases.removeAt(position)
        notifyItemRemoved(position)
    }
}