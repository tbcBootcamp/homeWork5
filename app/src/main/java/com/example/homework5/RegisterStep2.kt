package com.example.homework5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.homework5.databinding.ActivityRegisterStep2Binding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class RegisterStep2 : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterStep2Binding
    private lateinit var auth: FirebaseAuth

    private lateinit var etUsername: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        etUsername = binding.etUsername


        binding.btnSignUp.setOnClickListener {
            val user = intent.getParcelableExtra<User>("REGISTER_ARGUMENT")
            user?.let { onSignUp(it.name, it.password) }
        }
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }

        val user = intent.getParcelableExtra<User>("REGISTER_ARGUMENT")
    }

    private fun onSignUpClick() {
        val username = etUsername.text.toString()

        val user = auth.currentUser
        if (user != null) {
            saveUsername(user, username)
            switchToLoggedInActivity()
        }

    }

    private fun saveUsername(user: FirebaseUser, username: String) {

        val database = FirebaseDatabase.getInstance()
        val usersReference = database.getReference("users")

        val userEntry = HashMap<String, String>()
        userEntry["username"] = username

        usersReference.child(user.uid).setValue(userEntry)
    }

    private fun switchToLoggedInActivity() {
        val intent = Intent(this, LoggedInActivity::class.java)
        startActivity(intent)
    }

    private fun onSignUp(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        switchToLoggedInActivity()
                        Toast.makeText(
                            this,
                            "User added successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val user: FirebaseUser? = auth.currentUser
                    } else {
                        Toast.makeText(
                            this,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                this,
                "email and password cannot be empty",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}