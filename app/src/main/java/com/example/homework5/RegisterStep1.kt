package com.example.homework5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast

import com.example.homework5.databinding.ActivityRegisterStep1Binding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.parcelize.Parcelize

class RegisterStep1 : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterStep1Binding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()



        binding.ibBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(
                    this,
                    "fill the fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isEmailValid(email)){
                Toast.makeText(
                    this,
                    "incorrect email",
                    Toast.LENGTH_SHORT
                ).show()
            }else if (!validPassword(password)){
                Toast.makeText(
                    this,
                    "enter at least 6 char ",
                    Toast.LENGTH_SHORT
                ).show()
            } else{
                switchToRegisterStep2(email, password)
            }
        }
    }


    private fun switchToRegisterStep2(email: String, password: String) {
        val intent = Intent(this, RegisterStep2::class.java)
        intent.putExtra("REGISTER_ARGUMENT", User(email, password))
        startActivity(intent)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun validPassword(password: String): Boolean {
        return password.length >= 6
    }

}

@Parcelize
data class User(
    val name: String,
    val password: String
) : Parcelable