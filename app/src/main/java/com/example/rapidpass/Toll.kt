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

class Toll : AppCompatActivity() {

    private lateinit var scanResultTv2: TextView
    private lateinit var scanBtn: ImageButton
    private lateinit var fairListBtn: ImageButton
    private lateinit var complainBtn: ImageButton
    private lateinit var showMapBtn: ImageButton

    // ✅ QR Scanner launcher
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show()
        } else {
            scanResultTv2.text = "Scanned successful"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_toll)

        // ✅ Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Initialize views
        scanResultTv2 = findViewById(R.id.scanResultTv2)
        scanBtn = findViewById(R.id.scanBtn)
        fairListBtn = findViewById(R.id.imageButton4)
        complainBtn = findViewById(R.id.imageButton8)
        showMapBtn = findViewById(R.id.showMap)

        // ✅ Scan button click
        scanBtn.setOnClickListener {
            val options = ScanOptions()
            options.setPrompt("Scan the Toll QR")
            options.setBeepEnabled(true)
            options.setOrientationLocked(true)
            options.setCaptureActivity(CaptureActivity::class.java)
            barcodeLauncher.launch(options)
        }

        // ✅ Fair List button click
        fairListBtn.setOnClickListener {
            val intent = Intent(this, TollFairList::class.java)
            startActivity(intent)
        }

        // ✅ Complain button click
        complainBtn.setOnClickListener {
            val intent = Intent(this, Complain::class.java)
            startActivity(intent)
        }

        // ✅ Map button click
        showMapBtn.setOnClickListener {
            val intent = Intent(this, TollMapActivity::class.java)
            startActivity(intent)
        }
    }
}
