package com.example.homework5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            switchToRegisterStep1()
        }
        binding.btnLogIn.setOnClickListener {
            switchToLogIn()
        }

    }

    private fun switchToRegisterStep1() {
        val intent = Intent(this, RegisterStep1::class.java)
        startActivity(intent)
    }

    private fun switchToLogIn() {
        val intent = Intent(this, LogIn::class.java)
        startActivity(intent)
    }
}