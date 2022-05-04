package ie.wit.gymnote.ui.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ie.wit.gymnote.databinding.FragmentAddnoteBinding
import ie.wit.gymnote.databinding.FragmentLogoutBinding
import ie.wit.gymnote.loginLogout.FirebaseAuthManager
import ie.wit.gymnote.loginLogout.LoggedInViewModel


class LogoutFragment : Fragment() {

    private var _binding: FragmentLogoutBinding? = null
    private lateinit var loggedInViewModel : LoggedInViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)
        Toast.makeText(context, "Logging out", Toast.LENGTH_SHORT).show()
        loggedInViewModel.logOut()
        val intent = Intent(context, FirebaseAuthManager::class.java)
        startActivity(intent)
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}