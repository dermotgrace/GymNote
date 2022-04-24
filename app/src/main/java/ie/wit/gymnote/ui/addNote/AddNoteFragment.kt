package ie.wit.gymnote.ui.addNote

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ie.wit.gymnote.R
import ie.wit.gymnote.databinding.FragmentAddnoteBinding
import ie.wit.gymnote.fragmentCommunication.FragmentCommunicator
import ie.wit.gymnote.models.NoteModel
import ie.wit.gymnote.ui.notes.NotesFragment
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment() {
    private lateinit var communicator : FragmentCommunicator
    private var _binding: FragmentAddnoteBinding? = null
    private lateinit var noteDatePicker: TextView
    private lateinit var btnDatePicker: Button
    private lateinit var btnAdd: Button
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var note = NoteModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(AddNoteViewModel::class.java)

        _binding = FragmentAddnoteBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddNoteFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDatePicker = view.findViewById(R.id.noteDate)
        btnDatePicker = view.findViewById(R.id.btnDatePicker)
        btnAdd = view.findViewById(R.id.btnAdd)
        communicator = activity as FragmentCommunicator

        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(calendar)
        }

        btnDatePicker.setOnClickListener {
                // TODO send existing date to note
                DatePickerDialog(
                    view.context,
                    datePicker,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()

        }

        btnAdd.setOnClickListener() {
            note.noteTitle = _binding?.noteTitle?.text.toString()
            note.noteDate = _binding?.noteDate?.text.toString()
            note.noteDetail = _binding?.noteDetails?.text.toString()

            if (note.noteTitle.isNotEmpty() && note.noteDate.isNotEmpty() && note.noteDetail.isNotEmpty()) {
                Timber.i("add Button Pressed: ${note}")
                communicator.passData(note.noteTitle, note.noteDate, note.noteDetail)
                // Navigate back to notes list
                view.findNavController().navigate(R.id.navigation_notes)

            }
            else {
                Snackbar.make(it,"Please ensure all fields are completed", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun updateLabel(calendar: Calendar) {
        val format = "dd-MM-yyyy";
        val sdf = SimpleDateFormat(format, Locale.UK)
        noteDatePicker.setText(sdf.format(calendar.time))
    }
}