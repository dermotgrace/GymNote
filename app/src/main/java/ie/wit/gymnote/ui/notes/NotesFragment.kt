package ie.wit.gymnote.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val noteTitle =  arguments?.getString("noteTitle")
        val noteDate =  arguments?.getString("noteDate")
        val noteDetails =  arguments?.getString("noteDetails")
        i("Notes received")
        i("noteTitle received: ${noteTitle}")
        i("noteDate received: ${noteDate}")
        i("noteDetails received: ${noteDetails}")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}