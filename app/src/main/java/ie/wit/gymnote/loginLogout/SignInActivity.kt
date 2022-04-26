package ie.wit.gymnote.loginLogout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import ie.wit.gymnote.MainActivity
import ie.wit.gymnote.R
import ie.wit.gymnote.databinding.ActivitySignInBinding
import ie.wit.gymnote.databinding.ActivitySignUpBinding
import ie.wit.gymnote.models.LoadingViewModel
import timber.log.Timber.i

class SignInActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: LoadingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        _binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(_binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        _binding.notRegistered.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            i("starting sign up activity")
            startActivity(intent)
        }

        _binding.signInButton.setOnClickListener{

            val email = _binding.email.text.toString()
            val password = _binding.password.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                try {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                        if(it.isSuccessful) {
                            Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }


            } else {
                Toast.makeText(this, "Please ensure all fields are complete", Toast.LENGTH_SHORT).show()
            }
        }
    }
}