package ie.wit.gymnote.ui.addNote

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ie.wit.gymnote.R
import ie.wit.gymnote.databinding.FragmentAddnoteBinding
import ie.wit.gymnote.fragmentCommunication.FragmentCommunicator
import ie.wit.gymnote.loginLogout.LoggedInViewModel
import ie.wit.gymnote.models.NoteModel
import timber.log.Timber
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : Fragment() {
    private lateinit var communicator : FragmentCommunicator
    private var _binding: FragmentAddnoteBinding? = null
    private lateinit var noteDatePicker: TextView
    private lateinit var btnDatePicker: Button
    private lateinit var btnAdd: Button
    private lateinit var noteViewModel: AddNoteViewModel
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private var editing = "false"
    private lateinit var exerciseTypeSelect: AutoCompleteTextView
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

        noteViewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        noteViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        val exerciseTypes = arrayOf("Weightlifting","Gymnastics","Conditioning","Other")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, exerciseTypes)
        _binding!!.autoCompleteText.setAdapter(arrayAdapter)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddNoteFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDatePicker = view.findViewById(R.id.noteDate)
        btnDatePicker = view.findViewById(R.id.btnDatePicker)
        btnAdd = view.findViewById(R.id.btnAdd)
        exerciseTypeSelect = view.findViewById(R.id.autoCompleteText)
        communicator = activity as FragmentCommunicator

        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(calendar)
        }

        editing = arguments?.getString("editing").toString()

        btnDatePicker.setOnClickListener {
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            if (editing == "true") {
                val noteDateStr = arguments?.getString("noteDate").toString()
                val delimiter = "-"
                val splat = noteDateStr.split(delimiter)
                i("gn splat ${splat}")
                year = splat.get(2).toInt()
                month = splat.get(1).toInt() - 1
                day = splat.get(0).toInt()
            }

            DatePickerDialog(
                view.context,
                datePicker,
                year,
                month,
                day
            ).show()
        }

        if(editing == "true") {
            val addEditButton : TextView? = view?.findViewById(R.id.btnAdd)
            val dateButton : TextView? = view?.findViewById(R.id.btnDatePicker)
            val noteTitle : TextView? = view?.findViewById(R.id.noteTitle)
            val noteDate : TextView? = view?.findViewById(R.id.noteDate)
            val noteDetails : TextView? = view?.findViewById(R.id.noteDetails)
            val exerciseType : AutoCompleteTextView? = view?.findViewById(R.id.autoCompleteText)
            addEditButton?.text = "Update Note"
            dateButton?.text = "Change Date"
            i("gn editing ${editing}")
            noteTitle?.text = arguments?.getString("noteTitle").toString()
            noteDate?.text = arguments?.getString("noteDate").toString()
            noteDetails?.text = arguments?.getString("noteDetail").toString()
            exerciseType?.setText(arguments?.getString("exerciseType").toString())

            note.uid = arguments?.getString("uid").toString()

        }

        btnAdd.setOnClickListener() {
            Timber.i("gn add Button Pressed1")
            note.noteTitle = _binding?.noteTitle?.text.toString()
            note.noteDate = _binding?.noteDate?.text.toString()
            note.noteDetail = _binding?.noteDetails?.text.toString()
            note.exerciseType = _binding?.autoCompleteText?.text.toString()

            if (note.noteTitle!!.isNotEmpty() && note.noteDate!!.isNotEmpty() && note.noteDetail!!.isNotEmpty()) {
                // communicator.passData(note.noteTitle!!, note.noteDate!!, note.noteDetail!!)
                if (editing != "true") {
                    i("gn adding note: ${note}")
                    noteViewModel.addNote(
                        loggedInViewModel.liveFirebaseUser,
                        NoteModel(
                            noteTitle = note.noteTitle,
                            noteDate = note.noteDate,
                            noteDetail = note.noteDetail,
                            exerciseType = note.exerciseType
                        )
                    )
                    Toast.makeText(context, "Note Created", Toast.LENGTH_SHORT).show()
                } else {
                    i("gn updating note: ${note}")

                    noteViewModel.updateNote(
                        loggedInViewModel.liveFirebaseUser,
                        NoteModel(
                            uid = note.uid,
                            noteTitle = note.noteTitle,
                            noteDate = note.noteDate,
                            noteDetail = note.noteDetail,
                            exerciseType = note.exerciseType
                        )
                    )

                    Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show()
                }

                // Navigate back to notes list
                try {
                    i("gn navigating to notes fragment")
                    view.findNavController().navigate(R.id.navigation_notes)
                } catch (ex: Throwable) {
                    Toast.makeText(context, "Error when updating note",Toast.LENGTH_SHORT)
                }
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