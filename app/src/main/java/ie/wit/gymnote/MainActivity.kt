package ie.wit.gymnote

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ie.wit.gymnote.databinding.ActivityMainBinding
import ie.wit.gymnote.fragmentCommunication.FragmentCommunicator
import ie.wit.gymnote.models.LoadingViewModel
import ie.wit.gymnote.ui.addNote.AddNoteFragment
import ie.wit.gymnote.ui.notes.NotesFragment
import timber.log.Timber
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var binding: ActivityMainBinding
    private val viewModel:LoadingViewModel by viewModels()


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

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        Timber.plant(Timber.DebugTree())

        // Start the app with Notes Fragment
        // val notesFragment = NotesFragment()
        // supportFragmentManager.beginTransaction().replace(R.id.container, notesFragment).commit()
    }

    override fun passData(noteTitle: String, noteDate: String, noteDetails: String) {
        // implementation of passData() in FragmentCommunicator class
        val bundle = Bundle()
        bundle.putString("noteTitle", noteTitle);
        bundle.putString("noteDate", noteDate);
        bundle.putString("noteDetails", noteDetails);

        val transaction = this.supportFragmentManager.beginTransaction()

        val notesFragment = NotesFragment()
        notesFragment.arguments = bundle
        // Passes data to notesFragment
        transaction.replace(R.id.container, notesFragment).commit()

    }


}