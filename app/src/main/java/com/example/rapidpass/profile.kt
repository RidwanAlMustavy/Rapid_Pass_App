package com.example.rapidpass

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class profile : AppCompatActivity() {

    private lateinit var btnUpdateProfile: Button
    private lateinit var profileIcon: ImageView
    private lateinit var txtUsername: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtId: TextView

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference.child("Users")
    private val storage = FirebaseStorage.getInstance()

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                uploadProfileImage(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        profileIcon = findViewById(R.id.profile_icon)
        txtUsername = findViewById(R.id.txt_username)
        txtEmail = findViewById(R.id.txt_email)
        txtId = findViewById(R.id.txt_user_id)
        btnUpdateProfile = findViewById(R.id.btn_update_profile)

        // Load profile data
        loadUserProfile()

        // Change profile picture
        profileIcon.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnUpdateProfile.setOnClickListener {
            startActivity(Intent(this, UpdateProfile::class.java))
        }
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid ?: return

        database.child(userId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val name = snapshot.child("name").value?.toString() ?: "User"
                val email = snapshot.child("email").value?.toString() ?: "No Email"
                val profileUrl = snapshot.child("profileImage").value?.toString() ?: ""

                txtUsername.text = name
                txtEmail.text = email
                txtId.text = "User ID: $userId"

                if (profileUrl.isNotEmpty()) {
                    // Load profile image from Firebase Storage without Glide
                    val storageRef = storage.getReferenceFromUrl(profileUrl)
                    val localFile = File.createTempFile("profile", "jpg")
                    storageRef.getFile(localFile)
                        .addOnSuccessListener {
                            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                            profileIcon.setImageBitmap(bitmap)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to load profile picture", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load profile: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadProfileImage(uri: Uri) {
        val userId = auth.currentUser?.uid ?: return
        val ref = storage.reference.child("profile_pictures/$userId.jpg")

        ref.putFile(uri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Save download URL in Firebase Database
                    database.child(userId).child("profileImage").setValue(downloadUri.toString())
                        .addOnSuccessListener {
                            // Load image from Storage manually
                            val storageRef = storage.getReferenceFromUrl(downloadUri.toString())
                            val localFile = File.createTempFile("profile", "jpg")
                            storageRef.getFile(localFile)
                                .addOnSuccessListener {
                                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                                    profileIcon.setImageBitmap(bitmap)
                                    Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT).show()
                                }
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
