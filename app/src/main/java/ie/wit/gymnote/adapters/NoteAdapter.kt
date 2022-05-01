package ie.wit.gymnote.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import ie.wit.gymnote.databinding.CardNoteBinding
import ie.wit.gymnote.models.NoteModel

class NoteAdapter constructor(private var notes: List<NoteModel>) :
    RecyclerView.Adapter<NoteAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardNoteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val note = notes[holder.adapterPosition]
        holder.bind(note)
    }

    override fun getItemCount(): Int = notes.size

    class MainHolder(private val binding : CardNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteModel) {
            binding.noteTitle.text = note.noteTitle
            binding.noteDate.text = note.noteDate
        }
    }
}