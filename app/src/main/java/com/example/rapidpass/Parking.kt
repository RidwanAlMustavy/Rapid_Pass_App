package com.example.rapidpass

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class Parking : AppCompatActivity() {

    private lateinit var scanBtn: ImageButton
    private lateinit var fareListBtn: ImageButton
    private lateinit var showMapBtn: ImageButton

    // ✅ QR Scanner launcher
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "QR Scan Successful", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_parking)

        // ✅ System bar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Initialize views (MATCH XML IDs)
        scanBtn = findViewById(R.id.scanBtn)
        fareListBtn = findViewById(R.id.fairlist)
        showMapBtn = findViewById(R.id.showMap)

        // ✅ Scan QR
        scanBtn.setOnClickListener {
            val options = ScanOptions()
            options.setPrompt("Scan Parking QR Code")
            options.setBeepEnabled(true)
            options.setOrientationLocked(true)
            options.setCaptureActivity(CaptureActivity::class.java)
            barcodeLauncher.launch(options)
        }

        // ✅ Fare List
        fareListBtn.setOnClickListener {
            startActivity(Intent(this, ParkingFareList::class.java))
        }

        // ✅ Show Map
        showMapBtn.setOnClickListener {
            startActivity(Intent(this, ParkingMapActivity::class.java))
        }
    }
}
