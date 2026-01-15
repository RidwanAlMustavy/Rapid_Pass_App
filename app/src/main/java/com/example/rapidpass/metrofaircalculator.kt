package com.example.rapidpass

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class metrofaircalculator : AppCompatActivity() {

    private val stations = listOf("Uttora North","Mirpur-10","Agargawo","Motijil","Jatrabari","Damra","Narayanganj")

    private val fareTable = arrayOf(
        intArrayOf(0, 10, 20, 40, 50, 60, 70),
        intArrayOf(10, 0, 10, 30, 40, 50, 60),
        intArrayOf(20, 10, 0, 10, 30, 40, 50),
        intArrayOf(40, 30, 10, 0, 20, 30, 40),
        intArrayOf(50, 40, 30, 20, 0, 10, 30),
        intArrayOf(60, 50, 40, 30, 10, 0, 20),
        intArrayOf(70, 60, 50, 40, 30, 20, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_metrofaircalculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerFrom: Spinner = findViewById(R.id.spinnerFrom)
        val spinnerTo: Spinner = findViewById(R.id.spinnerTo)
        val btnCalculate: Button = findViewById(R.id.btnCalculate)
        val result: TextView = findViewById(R.id.textViewResult)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        btnCalculate.setOnClickListener {
            val fromIndex = spinnerFrom.selectedItemPosition
            val toIndex = spinnerTo.selectedItemPosition
            val fare = fareTable[fromIndex][toIndex]
            result.text = "Fare: $fare BDT"
        }
    }
}
