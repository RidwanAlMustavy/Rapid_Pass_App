package com.example.rapidpass

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class registation : AppCompatActivity() {

    private lateinit var signin: Button
    private lateinit var confirm2: Button

    private lateinit var usernameEt: EditText
    private lateinit var phoneEt: EditText
    private lateinit var addressEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var cardNoEt: EditText
    private lateinit var dobEt: EditText
    private lateinit var genderRg: RadioGroup
    private lateinit var roleRg: RadioGroup

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        // Initialize views
        signin = findViewById(R.id.signin)
        confirm2 = findViewById(R.id.confirm2)
        usernameEt = findViewById(R.id.editTextText)
        phoneEt = findViewById(R.id.editTextNumber2)
        addressEt = findViewById(R.id.editTextText4)
        emailEt = findViewById(R.id.editTextTextEmailAddress)
        passwordEt = findViewById(R.id.editTextTextPassword2)
        cardNoEt = findViewById(R.id.editTextNumber3)
        dobEt = findViewById(R.id.editTextDate)
        genderRg = findViewById(R.id.radioGroup)
        roleRg = findViewById(R.id.radioGroup1)

        // Sign In button
        signin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Confirm button
        confirm2.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        val username = usernameEt.text.toString().trim()
        val phone = phoneEt.text.toString().trim()
        val address = addressEt.text.toString().trim()
        val email = emailEt.text.toString().trim()
        val password = passwordEt.text.toString().trim()
        val cardNo = cardNoEt.text.toString().trim()
        val dob = dobEt.text.toString().trim()

        // Gender selection
        val selectedGenderId = genderRg.checkedRadioButtonId
        val gender = if (selectedGenderId != -1) findViewById<RadioButton>(selectedGenderId).text.toString() else ""

        // Role selection
        val selectedRoleId = roleRg.checkedRadioButtonId
        val role = if (selectedRoleId != -1) findViewById<RadioButton>(selectedRoleId).text.toString() else ""

        // Validation
        when {
            username.isEmpty() -> usernameEt.error = "Enter username"
            phone.isEmpty() -> phoneEt.error = "Enter phone"
            address.isEmpty() -> addressEt.error = "Enter address"
            email.isEmpty() -> emailEt.error = "Enter email"
            password.isEmpty() -> passwordEt.error = "Enter password"
            cardNo.isEmpty() -> cardNoEt.error = "Enter card number"
            dob.isEmpty() -> dobEt.error = "Enter DOB"
            gender.isEmpty() -> Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show()
            role.isEmpty() -> Toast.makeText(this, "Select Role", Toast.LENGTH_SHORT).show()
            else -> saveToFirebase(username, phone, address, email, password, cardNo, dob, gender, role)
        }
    }

    private fun saveToFirebase(
        username: String, phone: String, address: String,
        email: String, password: String, cardNo: String,
        dob: String, gender: String, role: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val database = FirebaseDatabase.getInstance().getReference("Users")

                        // Save user in proper JSON structure
                        val user = mapOf(
                            "username" to username,
                            "phone" to phone,
                            "address" to address,
                            "email" to email,
                            "cardNo" to cardNo,
                            "dob" to dob,
                            "gender" to gender,
                            "role" to role,
                            "status" to if (role.lowercase() == "admin") "pending" else "active",
                            "totalRecharge" to 0.0
                        )

                        database.child(uid).setValue(user)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, dashboard::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this, "Database Error: ${dbTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Failed to get user ID", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Auth Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
