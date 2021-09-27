package com.nikitabolshakov.pictureoftheday.presentation.view.cases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.nikitabolshakov.pictureoftheday.data.local.cases.SimpleCase
import com.nikitabolshakov.pictureoftheday.data.local.repository.CasesRepository
import com.nikitabolshakov.pictureoftheday.data.local.repository.CasesRepositoryImpl
import com.nikitabolshakov.pictureoftheday.databinding.FragmentListOfCasesBinding

class ListOfCasesFragment(
    private val casesRepository: CasesRepository = CasesRepositoryImpl()
) : Fragment() {

    private val adapter: ListOfCasesAdapter by lazy { ListOfCasesAdapter() }

    private var _binding: FragmentListOfCasesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfCasesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listOfCasesRv.adapter = adapter
        binding.listOfCasesRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayout.VERTICAL
            )
        )
        adapter.cases = casesRepository.getCases()

        binding.addCase.setOnClickListener {
            adapter.addCase(SimpleCase("новое дело"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


/* class NotesAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
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
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_simple_case, parent, false)
            SimpleVH(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_complex_case, parent, false)
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

    fun addNote(note: ItemData) {
        notes.add(note)
        notifyItemInserted(notes.size - 1)
    }

    fun deleteNote(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = notes.size

    override fun getItemViewType(position: Int): Int =
        if (notes[position] is SimpleNote) {
            SIMPLE_TYPE
        } else {
            COMPLEX_TYPE
        }

    inner class SimpleVH(view: View) : BaseViewHolder(view) {
        private val text = view.findViewById<TextView>(R.id.text_simple_case)

        init {
            view.setOnClickListener {
                deleteNote(layoutPosition)
            }
        }

        fun bind(message: SimpleNote) {
            text.text = message.text
        }
    }

    inner class ComplexVH(view: View) : BaseViewHolder(view) {
        private val heading = view.findViewById<TextView>(R.id.heading_complex_case)
        private val text = view.findViewById<TextView>(R.id.text_complex_case)
        private val image = view.findViewById<ImageView>(R.id.image_complex_case)

        fun bind(message: ComplexNote) {
            heading.text = message.heading
            text.text = message.isImageVisible.toString()
            image.setOnClickListener {
                layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                    notes.removeAt(currentPosition).apply {
                        notes.add(currentPosition - 1, this)
                    }
                    //notifyItemChanged(currentPosition)
                    //notifyItemChanged(currentPosition - 1)
                    notifyItemMoved(currentPosition, currentPosition - 1)
                }
                //text.text = message.isImageVisible.toString()
                //val model = (notes[layoutPosition] as ComplexNote)
                //model.isImageVisible = !model.isImageVisible
                //notifyItemChanged(layoutPosition)
            }
        }
    }

    open class ItemData

    data class SimpleNote(
        val text: String
    ) : ItemData()

    data class ComplexNote(
        val heading: String,
        val text: String,
        var isImageVisible: Boolean = false
    ) : ItemData()
} */