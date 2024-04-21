package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed(3000){

            if(Firebase.auth.currentUser!=null)
            {  //val intent = Intent(this, QuizActivity::class.java )
               // startActivity(intent)

                val intent = Intent(this, LoginActivity::class.java )
                startActivity(intent)
            }

            else
            {
                val intent = Intent(this, LoginActivity::class.java )
                intent.putExtra("MODE","SIGNUP")
                startActivity(intent)
            }

        }
    }
}