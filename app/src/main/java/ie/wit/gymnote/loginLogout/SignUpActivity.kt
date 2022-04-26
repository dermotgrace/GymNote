package ie.wit.gymnote.loginLogout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ie.wit.gymnote.R
import ie.wit.gymnote.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var _binding:ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        _binding.alreadyRegistered.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        _binding.signUpButton.setOnClickListener{
            val email = _binding.email.text.toString()
            val password = _binding.password.text.toString()
            val confirmPassword = _binding.confirmPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if(password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if(it.isSuccessful) {
                            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please ensure all fields are complete", Toast.LENGTH_SHORT).show()
            }
        }
    }
}