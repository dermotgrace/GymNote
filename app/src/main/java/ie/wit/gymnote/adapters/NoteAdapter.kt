package ie.wit.gymnote.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import ie.wit.gymnote.databinding.CardNoteBinding
import ie.wit.gymnote.models.NoteModel
import timber.log.Timber.i

interface NoteListener {
    fun onNoteClick(note: NoteModel)
    fun onNoteCompleteClick(note: NoteModel)
    fun onNoteDeleteClick(note: NoteModel)
    fun completeSwitchClick()
}

class NoteAdapter constructor(private var notes: List<NoteModel>,
                              private val listener: NoteListener ) :
    RecyclerView.Adapter<NoteAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardNoteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val note = notes[holder.adapterPosition]
        holder.bind(note, listener)
    }

    override fun getItemCount(): Int = notes.size

    class MainHolder(private val binding : CardNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteModel, listener: NoteListener) {
            binding.noteTitle.text = note.noteTitle
            binding.noteDate.text = note.noteDate
            binding.root.setOnClickListener { listener.onNoteClick(note) }
            binding.completeNoteCheckIcon.setOnClickListener { listener.onNoteCompleteClick(note) }
            binding.deleteNoteIcon.setOnClickListener { listener.onNoteDeleteClick(note) }
        }
    }


}