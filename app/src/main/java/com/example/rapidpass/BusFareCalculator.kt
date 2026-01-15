package com.example.rapidpass

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BusFareCalculator : AppCompatActivity() {

    private lateinit var fromSpinner: Spinner
    private lateinit var toSpinner: Spinner
    private lateinit var calculateBtn: Button
    private lateinit var resultTv: TextView

    private val stations = arrayOf("Kuril", "Notun Bazar", "Badda", "Rampura", "Hatirjheel")

    // Fare table: From x To
    private val fareTable = arrayOf(
        intArrayOf(0, 10, 20, 30, 40),
        intArrayOf(10, 0, 10, 20, 30),
        intArrayOf(20, 10, 0, 10, 20),
        intArrayOf(30, 20, 10, 0, 10),
        intArrayOf(40, 30, 20, 10, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bus_fare_calculator)

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

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter

        calculateBtn.setOnClickListener {
            val fromIndex = fromSpinner.selectedItemPosition
            val toIndex = toSpinner.selectedItemPosition

            val fare = if (fromIndex == toIndex) 0 else fareTable[fromIndex][toIndex]
            resultTv.text = "Fare: $fare Taka"
        }
    }
}
