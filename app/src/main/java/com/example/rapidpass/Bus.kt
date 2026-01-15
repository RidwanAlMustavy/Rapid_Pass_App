package com.example.rapidpass

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class Bus : AppCompatActivity() {

    private lateinit var scanResultTv: TextView
    private lateinit var scanBtn: ImageButton
    private lateinit var fareList: ImageButton
    private lateinit var fareCalculator: ImageButton
    private lateinit var complain: ImageButton
    private lateinit var showMap: ImageButton


    // ✅ QR Scanner launcher
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show()
        } else {
            scanResultTv.text = "Scanned successful"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bus)

        // ✅ Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Initialize views
        scanResultTv = findViewById(R.id.scanResultTv)
        scanBtn = findViewById(R.id.scanBtn)
        fareList = findViewById(R.id.imageButton4)
        fareCalculator = findViewById(R.id.imageButton9)
        complain = findViewById(R.id.imageButton10)

        // ✅ Scan button click
        scanBtn.setOnClickListener {
            val options = ScanOptions()
            options.setPrompt("Scan the Bus Ticket")
            options.setBeepEnabled(true)
            options.setOrientationLocked(true)
            options.setCaptureActivity(CaptureActivity::class.java)
            barcodeLauncher.launch(options)
        }

        // ✅ Fare List button click
        fareList.setOnClickListener {
            val intent = Intent(this, busfair::class.java)
            startActivity(intent)
        }

        // ✅ Fare Calculator button click
        fareCalculator.setOnClickListener {
            val intent = Intent(this, BusFareCalculator::class.java)
            startActivity(intent)
        }

        // ✅ Complain button click
        complain.setOnClickListener {
            val intent = Intent(this, Complain::class.java)
            startActivity(intent)
        }
        showMap = findViewById(R.id.showMap)
        showMap.setOnClickListener {
            val intent = Intent(this, BusMapActivity::class.java)
            startActivity(intent)
        }
    }
}
