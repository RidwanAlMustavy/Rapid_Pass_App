package com.example.rapidpass

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ShipFareCalculator : AppCompatActivity() {

    private lateinit var fromSpinner: Spinner
    private lateinit var toSpinner: Spinner
    private lateinit var calculateBtn: Button
    private lateinit var resultTv: TextView

    // Ports
    private val ports = arrayOf("Dhaka", "Chandpur", "Feni", "Chattogram", "Cox's Bazar", "Saint Martin")

    // Fare table: From x To (example fares in Taka)
    private val fareTable = arrayOf(
        intArrayOf(0, 500, 700, 1000, 1500, 2000),   // Dhaka
        intArrayOf(500, 0, 300, 600, 1100, 1600),   // Chandpur
        intArrayOf(700, 300, 0, 400, 900, 1400),    // Feni
        intArrayOf(1000, 600, 400, 0, 500, 1000),   // Chattogram
        intArrayOf(1500, 1100, 900, 500, 0, 600),   // Cox's Bazar
        intArrayOf(2000, 1600, 1400, 1000, 600, 0)  // Saint Martin
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ship_fare_calculator)

        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        fromSpinner = findViewById(R.id.spinnerFrom)
        toSpinner = findViewById(R.id.spinnerTo)
        calculateBtn = findViewById(R.id.btnCalculate)
        resultTv = findViewById(R.id.textViewResult)

        // Spinner adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ports)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter

        // Calculate fare
        calculateBtn.setOnClickListener {
            val fromIndex = fromSpinner.selectedItemPosition
            val toIndex = toSpinner.selectedItemPosition

            val fare = if (fromIndex == toIndex) 0 else fareTable[fromIndex][toIndex]
            resultTv.text = "Fare: $fare Taka"
        }
    }
}
