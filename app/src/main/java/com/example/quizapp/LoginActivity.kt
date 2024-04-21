package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.button.setOnClickListener{
            val email = binding.email?.text.toString()
            val password = binding.password?.text.toString()

            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        Toast.makeText(this, "User Logged In!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, QuizActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign-in fails, try to create a new account
                        Firebase.auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { createTask ->
                                if (createTask.isSuccessful) {
                                    Toast.makeText(this, "User Created!", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, QuizActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    //change
                                    // Handle specific exceptions
                                    when (createTask.exception) {
                                        is FirebaseAuthUserCollisionException -> {
                                            // User already exists, try to sign in again
                                            signInUser(email, password)
                                        }
                                        else -> {
                                            // Handle other exceptions
                                            Toast.makeText(this, "Authentication Failed: ${createTask.exception?.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                    }
                }
        }
    }

    private fun signInUser(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
                    Toast.makeText(this, "User Logged In!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, QuizActivity::class.java)
                    startActivity(intent)
                } else {
                    // Handle sign-in failure
                    Toast.makeText(this, "Authentication Failed: ${signInTask.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
