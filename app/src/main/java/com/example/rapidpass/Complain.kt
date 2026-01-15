package com.example.rapidpass

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Complain : AppCompatActivity() {

    private lateinit var submit: Button
    private lateinit var complainEditText: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_complain)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        submit = findViewById(R.id.btn_submit)
        complainEditText = findViewById(R.id.editTextText2)
        auth = FirebaseAuth.getInstance()

        val database = FirebaseDatabase.getInstance()
        val complaintsRef = database.getReference("complaints")

        submit.setOnClickListener {
            val complaintText = complainEditText.text.toString().trim()

            if (complaintText.isEmpty()) {
                Toast.makeText(this, "Please enter your complaint", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = auth.currentUser?.uid
            val user = auth.currentUser?.displayName ?: "Unknown"

            if (uid != null) {
                // Use proper JSON structure with user info
                val complaintId = complaintsRef.push().key
                if (complaintId != null) {
                    val complaintData = mapOf(
                        "user" to user,
                        "uid" to uid,
                        "text" to complaintText,
                        "timestamp" to System.currentTimeMillis()
                    )

                    complaintsRef.child(complaintId).setValue(complaintData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Complaint submitted successfully", Toast.LENGTH_SHORT).show()
                            complainEditText.text.clear()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to submit complaint: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(this, "You must be logged in to submit a complaint", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
