package ie.wit.gymnote.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.gymnote.adapters.NoteAdapter
import ie.wit.gymnote.databinding.FragmentNotesBinding
import ie.wit.gymnote.models.NoteModel
import timber.log.Timber
import timber.log.Timber.i

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    val notes = ArrayList<NoteModel>();

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(NotesViewModel::class.java)
        i("homeviewmodel")
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        i("inflated fragmentNotesBinding")
        val root: View = binding.root

/*        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        val note = NoteModel()
        note.noteTitle = arguments?.getString("noteTitle").toString()
        note.noteDate = arguments?.getString("noteDate").toString()
        note.noteDetail = arguments?.getString("noteDetail").toString()
        i("Notes received")
        i("noteTitle received: ${note.noteTitle}")
        i("noteDate received: ${note.noteDate}")
        i("noteDetails received: ${note.noteDetail}")

        val layoutManager = LinearLayoutManager(context)
        _binding!!.recyclerView.layoutManager = layoutManager
        _binding!!.recyclerView.adapter = NoteAdapter(notes)

        notes.add(note)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}