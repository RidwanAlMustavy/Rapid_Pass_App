package com.example.rapidpass

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupBtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEt = findViewById(R.id.editTextNumber)
        passwordEt = findViewById(R.id.editTextTextPassword)
        loginBtn = findViewById(R.id.confirm)
        signupBtn = findViewById(R.id.signup)

        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        signupBtn.setOnClickListener {
            startActivity(Intent(this, registation::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val uid = auth.currentUser?.uid
                    if (uid == null) {
                        Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }

                    // Read role in a safe way
                    val userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)

                    userRef.get().addOnSuccessListener { snapshot ->

                        // Default to "user"
                        val role = snapshot.child("role").value?.toString()?.lowercase() ?: "user"

                        if (role == "admin") {
                            startActivity(Intent(this, Admin::class.java))
                        } else {
                            startActivity(Intent(this, dashboard::class.java))
                        }

                        finish()

                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed to get user role", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
