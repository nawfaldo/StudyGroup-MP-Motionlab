package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameEditText = findViewById<EditText>(R.id.nameInput)
        val passwordEditText = findViewById<EditText>(R.id.passInput)

        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isEmpty() || password.isEmpty()) {
                Log.e(TAG, "Login failed: Username or password is empty")
                Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                Log.i(TAG, "Login successful for user: $name")
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                val goToHome = Intent(this, HomeActivity::class.java)
                startActivity(goToHome)
                finish()
            }
        }
    }
}
