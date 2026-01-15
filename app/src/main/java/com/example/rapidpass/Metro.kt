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

class Metro : AppCompatActivity() {

    lateinit var scanResultTv: TextView
    lateinit var scanBtn: ImageButton
    lateinit var showMap: ImageButton
    lateinit var fareList: ImageButton
    lateinit var fairCalculator: ImageButton
    lateinit var complain: ImageButton







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_metro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        scanResultTv = findViewById(R.id.scanResultTv)
        scanBtn = findViewById(R.id.scanBtn)
        scanBtn.setOnClickListener {
            val options = ScanOptions()
            options.setPrompt("Scan the Metro Ticket")
            options.setBeepEnabled(true)
            options.setOrientationLocked(true)
            options.setCaptureActivity(CaptureActivity::class.java)
            barcodeLauncher.launch(options)

        }

        showMap = findViewById(R.id.showMap)
        showMap.setOnClickListener {
            val intent = Intent(this, Showmap::class.java)
            startActivity(intent)
        }

        fareList= findViewById(R.id.imageButton4)
        fareList.setOnClickListener {
            val intent = Intent(this, MetroFareList::class.java)
            startActivity(intent)
        }
        fairCalculator= findViewById(R.id.imageButton6)
        fairCalculator.setOnClickListener {
            val intent = Intent(this, metrofaircalculator::class.java)
            startActivity(intent)
        }
        complain= findViewById(R.id.imageButton7)
        complain.setOnClickListener {
            val intent = Intent(this, Complain::class.java)
            startActivity(intent)
        }



    }
    private val barcodeLauncher = registerForActivityResult(ScanContract())
    {
        result ->
        if (result.contents != null) {
            scanResultTv.text = "Scanned successful"
        }
        else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }

    }

}