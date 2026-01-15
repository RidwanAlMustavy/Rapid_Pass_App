package com.example.rapidpass

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class dashboard : AppCompatActivity() {

    private lateinit var bus: Button
    private lateinit var metro: Button
    private lateinit var ship: Button
    private lateinit var toll: Button
    private lateinit var parking: Button
    private lateinit var shopping: Button
    private lateinit var helpline: Button
    private lateinit var recharge: Button
    private lateinit var imageButton3: ImageButton
    private lateinit var tvBalance: TextView

    // Firebase
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId get() = auth.currentUser?.uid
    private val balanceRef get() = userId?.let { database.getReference("Users").child(it).child("balance") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Views
        bus = findViewById(R.id.bus)
        metro = findViewById(R.id.metro)
        ship = findViewById(R.id.ship)
        toll = findViewById(R.id.toll)
        parking = findViewById(R.id.parking)
        shopping = findViewById(R.id.shopping)
        helpline = findViewById(R.id.helpline)
        recharge = findViewById(R.id.recharge)
        imageButton3 = findViewById(R.id.imageButton3)
        tvBalance = findViewById(R.id.balanceshow)

        // Button clicks
        bus.setOnClickListener { startActivity(Intent(this, Bus::class.java)) }
        metro.setOnClickListener { startActivity(Intent(this, Metro::class.java)) }
        ship.setOnClickListener { startActivity(Intent(this, Ship::class.java)) }
        toll.setOnClickListener { startActivity(Intent(this, Toll::class.java)) }
        parking.setOnClickListener { startActivity(Intent(this, Parking::class.java)) }
        shopping.setOnClickListener { startActivity(Intent(this, Shopping::class.java)) }
        helpline.setOnClickListener { startActivity(Intent(this, Helpline::class.java)) }
        recharge.setOnClickListener { startActivity(Intent(this, BkashActivity::class.java)) }
        imageButton3.setOnClickListener { startActivity(Intent(this, Menu::class.java)) }

        // Listen for real-time balance changes
        listenBalanceChanges()
    }

    private fun listenBalanceChanges() {
        if (balanceRef == null) {
            tvBalance.text = "Balance: 0 BDT"
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        balanceRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val balance = snapshot.getValue(Float::class.java) ?: 0f
                tvBalance.text = "Balance: $balance BDT"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@dashboard, "Failed to load balance", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
