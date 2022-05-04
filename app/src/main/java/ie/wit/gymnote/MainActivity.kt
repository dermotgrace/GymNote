package ie.wit.gymnote

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ie.wit.gymnote.databinding.ActivityMainBinding
import ie.wit.gymnote.fragmentCommunication.FragmentCommunicator
import ie.wit.gymnote.models.LoadingViewModel
import ie.wit.gymnote.models.NoteModel
import ie.wit.gymnote.ui.addNote.AddNoteFragment
import ie.wit.gymnote.ui.notes.NotesFragment
import timber.log.Timber
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var binding: ActivityMainBinding
    private val viewModel:LoadingViewModel by viewModels()
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_notes, R.id.navigation_addnote, R.id.navigation_logout
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        Timber.plant(Timber.DebugTree())

        // Start the app with Notes Fragment
        // val notesFragment = NotesFragment()
        // supportFragmentManager.beginTransaction().replace(R.id.container, notesFragment).commit()
    }


    override fun passDataToNotes(note: NoteModel) {
        // implementation of passData() in FragmentCommunicator class
        val bundle = Bundle()
        bundle.putString("noteTitle", note?.noteTitle);
        bundle.putString("noteDate", note?.noteDate);
        bundle.putString("noteDetail", note?.noteDetail);

        val transaction = this.supportFragmentManager.beginTransaction()

        val notesFragment = NotesFragment()
        notesFragment.arguments = bundle
        navController.navigate(R.id.navigation_notes)
        // Passes data to notesFragment
        transaction.replace(R.id.container, notesFragment).commit()

    }

    override fun passDataToEditNote(note: NoteModel) {
        // implementation of passData() in FragmentCommunicator class
        i("gn passDataToEditNote()")
        val bundle = Bundle()
        bundle.putString("noteTitle", note?.noteTitle);
        bundle.putString("noteDate", note?.noteDate);
        bundle.putString("noteDetail", note?.noteDetail);
        bundle.putString("editing", "true");
        bundle.putString("uid", note?.uid);
        val transaction = this.supportFragmentManager.beginTransaction()

        val noteFragment = AddNoteFragment()
        noteFragment.arguments = bundle
        navController.navigate(R.id.navigation_addnote)
        // Passes data to notesFragment
        transaction.replace(R.id.container, noteFragment).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    // Not staying here
    fun completeNote(e: View) {
        i("gn completeNoteCheckClick ${e.toString()}")
    }

    fun deleteNote(e: View) {
        i("gn deleteNote ${e.toString()}")
    }

}