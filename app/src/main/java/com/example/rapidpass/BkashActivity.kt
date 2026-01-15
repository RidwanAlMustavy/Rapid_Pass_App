package com.example.rapidpass

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class BkashActivity : AppCompatActivity() {

    private lateinit var etPhone: EditText
    private lateinit var etAmount: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnPay: Button
    private lateinit var tvBalance: TextView

    private var balance: Float = 0f

    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId get() = auth.currentUser?.uid

    private val balanceRef
        get() = if (userId != null) database.getReference("Users").child(userId!!).child("balance") else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bkash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etPhone = findViewById(R.id.etPhoneNumber)
        etAmount = findViewById(R.id.etAmount)
        etPassword = findViewById(R.id.etPassword)
        btnPay = findViewById(R.id.btnPay)
        tvBalance = findViewById(R.id.tvBalance)

        // Load previous balance
        loadBalanceFromFirebase()

        btnPay.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            val amountText = etAmount.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (phone.isEmpty() || amountText.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toFloatOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performPayment(amount)
        }
    }

    private fun loadBalanceFromFirebase() {
        if (balanceRef == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        balanceRef!!.get().addOnSuccessListener {
            balance = it.getValue(Float::class.java) ?: 0f
            tvBalance.text = "Balance: $balance BDT"
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load balance", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performPayment(amount: Float) {
        if (balanceRef == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        balance += amount
        balanceRef!!.setValue(balance).addOnSuccessListener {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
            tvBalance.text = "Balance: $balance BDT"
            etAmount.text.clear()
            etPhone.text.clear()
            etPassword.text.clear()
        }.addOnFailureListener {
            Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
