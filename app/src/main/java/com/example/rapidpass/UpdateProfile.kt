package com.example.rapidpass

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class UpdateProfile : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var btnBack: Button
    private lateinit var ivProfile: ImageView

    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 100
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        // Initialize Views
        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPhone = findViewById(R.id.edt_phone)
        edtPassword = findViewById(R.id.edt_password)
        btnSave = findViewById(R.id.btn_save_profile)
        btnBack = findViewById(R.id.btn_back)
        ivProfile = findViewById(R.id.iv_profile_pic)

        // Load current profile if exists
        loadUserProfile()

        // Pick image from gallery
        ivProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Save button
        btnSave.setOnClickListener {
            if (edtName.text.isEmpty() || edtEmail.text.isEmpty()) {
                Toast.makeText(this, "Name & Email are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imageUri != null) {
                uploadImageToFirebase()
            } else {
                saveDataToDatabase("") // No image selected
            }
        }

        // Back button
        btnBack.setOnClickListener { finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            ivProfile.setImageURI(imageUri)
        }
    }

    /** Upload image to Firebase Storage and save the download URL */
    private fun uploadImageToFirebase() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        val storageRef = FirebaseStorage.getInstance().reference.child("profile_pictures/$userId.jpg")

        imageUri?.let {
            storageRef.putFile(it)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        saveDataToDatabase(uri.toString())
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to get download URL: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Image upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    /** Save user info including profile image URL to Firebase Realtime Database */
    private fun saveDataToDatabase(imageUrl: String) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

        val user = mapOf(
            "name" to edtName.text.toString(),
            "email" to edtEmail.text.toString(),
            "phone" to edtPhone.text.toString(),
            "password" to edtPassword.text.toString(),
            "profileImage" to imageUrl
        )

        database.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    /** Load user profile from Firebase */
    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

        database.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                edtName.setText(snapshot.child("name").value?.toString() ?: "")
                edtEmail.setText(snapshot.child("email").value?.toString() ?: "")
                edtPhone.setText(snapshot.child("phone").value?.toString() ?: "")
                edtPassword.setText(snapshot.child("password").value?.toString() ?: "")

                val imageUrl = snapshot.child("profileImage").value?.toString()
                if (!imageUrl.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.profilepic)
                        .into(ivProfile)
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load profile: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
