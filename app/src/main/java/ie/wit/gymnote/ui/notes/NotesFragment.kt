package ie.wit.gymnote.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ie.wit.gymnote.R
import ie.wit.gymnote.adapters.NoteAdapter
import ie.wit.gymnote.adapters.NoteListener
import ie.wit.gymnote.databinding.FragmentNotesBinding
import ie.wit.gymnote.firebaseDB.FirebaseDBManager
import ie.wit.gymnote.fragmentCommunication.FragmentCommunicator
import ie.wit.gymnote.models.NoteModel
import timber.log.Timber
import timber.log.Timber.i


class NotesFragment : Fragment(), NoteListener {
    private lateinit var communicator : FragmentCommunicator
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val notesList =
        MutableLiveData<List<NoteModel>>()

    val observableNotesList: LiveData<List<NoteModel>>
        get() = notesList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

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

        i("gn calling observe()")
        observableNotesList.observe(viewLifecycleOwner, Observer {
                notes ->
            notes?.let {
                render(notes as ArrayList<NoteModel>)
            }
        })
        i("gn loading notes")
        loadNotes()

        communicator = activity as FragmentCommunicator
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
        binding?.recyclerView?.adapter = NoteAdapter(notesList, this)
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

    override fun onNoteClick(note: NoteModel) {
        i("gn editing note ${note}")
        communicator.passDataToEditNote(note)

/*        val intent = Intent(context, AddNoteFragment::class.java)
        startActivity(intent)*/
    }



}