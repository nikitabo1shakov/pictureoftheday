package com.nikitabolshakov.pictureoftheday.presentation.view.notes

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.behavior.SwipeDismissBehavior
import com.nikitabolshakov.pictureoftheday.R
import com.nikitabolshakov.pictureoftheday.databinding.FragmentNotesBinding
import com.nikitabolshakov.pictureoftheday.utils.hide
import com.nikitabolshakov.pictureoftheday.utils.toast

class NotesFragment : Fragment() {

    private val adapter: NotesAdapter by lazy { NotesAdapter() }

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notesList = mutableListOf(
            NotesAdapter.SimpleNote("Купить молоко"),
            NotesAdapter.ComplexNote("Супер важное", "Погладить кота. Улететь на луну."),
            NotesAdapter.ComplexNote("АБВ", "фывапролджфяцйрыыыыыыы."),
            NotesAdapter.SimpleNote("Пробежать марфон 20 км"),
            NotesAdapter.SimpleNote("Сделать то, непонятно что"),
            NotesAdapter.SimpleNote("Очень важное сообщение"),
            NotesAdapter.ComplexNote("Как бы заголовок", "Как бы текст")
        )

        binding.notesFragmentRecyclerView.adapter = adapter
        binding.notesFragmentRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        adapter.notes = notesList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view)

class NotesAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    companion object{
        private const val SIMPLE_TYPE = 1
        private const val COMPLEX_TYPE = 2
    }

    var notes = mutableListOf<ItemData>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        if (viewType == SIMPLE_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_simple_note, parent, false)
            SimpleVH(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_complex_note, parent, false)
            ComplexVH(view)
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        val currentNotes = notes[position]

        if (getItemViewType(position) == SIMPLE_TYPE) {
            (holder as SimpleVH).bind(currentNotes as SimpleNote)
        } else {
            (holder as ComplexVH).bind(currentNotes as ComplexNote)
        }
    }

    override fun getItemCount(): Int = notes.size

    override fun getItemViewType(position: Int): Int =
        if (notes[position] is SimpleNote) {
            SIMPLE_TYPE
        } else {
            COMPLEX_TYPE
        }

    private class SimpleVH(view: View): BaseViewHolder(view) {
        private val text = view.findViewById<TextView>(R.id.text_simple_note)

        fun bind(message: SimpleNote) {
            text.text = message.text
        }
    }

    private class ComplexVH(view: View): BaseViewHolder(view) {
        private val heading = view.findViewById<TextView>(R.id.heading_complex_note)
        private val text = view.findViewById<TextView>(R.id.text_complex_note)

        fun bind(message: ComplexNote) {
            heading.text = message.heading
            text.text = message.text
        }
    }

    open class ItemData

    data class SimpleNote(
        val text: String
    ): ItemData()

    data class ComplexNote(
        val heading: String,
        val text: String
    ): ItemData()
}