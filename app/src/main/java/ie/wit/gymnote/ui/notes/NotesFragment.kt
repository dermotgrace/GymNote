package ie.wit.gymnote.ui.notes

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ie.wit.gymnote.adapters.NoteAdapter
import ie.wit.gymnote.databinding.FragmentNotesBinding
import ie.wit.gymnote.firebaseDB.FirebaseDBManager
import ie.wit.gymnote.models.NoteModel
import ie.wit.gymnote.ui.addNote.AddNoteFragment
import timber.log.Timber
import timber.log.Timber.i


class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val notesList =
        MutableLiveData<List<NoteModel>>()

    val observableNotesList: LiveData<List<NoteModel>>
        get() = notesList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(NotesViewModel::class.java)
        i("gn homeviewmodel")
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        i("gn inflated fragmentNotesBinding")
        val root: View = binding.root
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

/*        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
/*        val note = NoteModel()
        note.noteTitle = arguments?.getString("noteTitle").toString()
        note.noteDate = arguments?.getString("noteDate").toString()
        note.noteDetail = arguments?.getString("noteDetail").toString()
        i("Notes received")
        i("noteTitle received: ${note.noteTitle}")
        i("noteDate received: ${note.noteDate}")
        i("noteDetails received: ${note.noteDetail}")

        val layoutManager = LinearLayoutManager(context)
        _binding!!.recyclerView.layoutManager = layoutManager
        _binding!!.recyclerView.adapter = NoteAdapter(notesList)

        // notes.add(note)

        Toast.makeText(context, "Note added successfully", Toast.LENGTH_SHORT).show()*/
        i("gn calling observe()")
        observableNotesList.observe(viewLifecycleOwner, Observer {
                notes ->
            notes?.let {
                render(notes as ArrayList<NoteModel>)
            }
        })
        i("gn after observe()")
        i("gn calling loadNotes()")
        loadNotes()
        i("gn after loadNotes()")
        return root
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            NotesFragment().apply {
                arguments = Bundle().apply { }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadNotes() {
        try {
            i("gn calling findAll()")
            FirebaseDBManager.findAll(FirebaseAuth.getInstance().getCurrentUser()?.getUid()!!, notesList)

            //FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, notesList)
            Timber.i("gn Notes Load Success : ${notesList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("gn Notes Load Error : $e.message")
        }
    }

    private fun render(notesList: ArrayList<NoteModel>) {
        i("gn render()")
        binding?.recyclerView?.adapter = NoteAdapter(notesList)
        if (notesList.isEmpty()) {
            i("gn notesList is empty()")
            binding?.recyclerView?.visibility = View.GONE
        } else {
            i("gn noteslist is not empty ${notesList}")
            binding?.recyclerView?.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }
}