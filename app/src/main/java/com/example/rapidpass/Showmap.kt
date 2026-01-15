package com.example.rapidpass

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Showmap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var gMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_showmap)

        // Apply system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the map fragment and initialize the map


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment

        mapFragment?.getMapAsync(this) ?: run {
            Toast.makeText(this, "Map failed to load", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap

        // Example location: Dhaka
        val dhaka = LatLng(23.8103, 90.4125)
        val uttora = LatLng(23.8752, 90.3853)
        val mirpur10 = LatLng(23.8090, 90.3663)
        val agargaon = LatLng(23.7580, 90.3856)
        val motijheel = LatLng(23.7290, 90.4125)
        val jatrabari = LatLng(23.7253, 90.4356)
        val damra = LatLng(23.7965, 90.3700)
        val narayanganj = LatLng(23.6220, 90.5000)
        gMap.addMarker(MarkerOptions().position(dhaka).title("Dhaka"))
        gMap.addMarker(MarkerOptions().position(uttora).title("Uttora North"))
        gMap.addMarker(MarkerOptions().position(mirpur10).title("Mirpur-10"))
        gMap.addMarker(MarkerOptions().position(agargaon).title("Agargaon"))
        gMap.addMarker(MarkerOptions().position(motijheel).title("Motijheel"))
        gMap.addMarker(MarkerOptions().position(jatrabari).title("Jatrabari"))
        gMap.addMarker(MarkerOptions().position(damra).title("Damra"))
        gMap.addMarker(MarkerOptions().position(narayanganj).title("Narayanganj"))
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 12f))
    }
}
