package com.example.rapidpass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ShipMapActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ship_map)

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Map Fragment
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Define ship locations
        val dhaka = LatLng(23.8103, 90.4125)
        val chandpur = LatLng(23.2175, 90.6615)
        val feni = LatLng(23.0146, 91.3966)
        val chattogram = LatLng(22.3569, 91.7832)
        val coxBazar = LatLng(21.4272, 92.0058)
        val saintMartin = LatLng(20.6230, 92.3123)

        // Add markers
        googleMap.addMarker(MarkerOptions().position(dhaka).title("Dhaka Port"))
        googleMap.addMarker(MarkerOptions().position(chandpur).title("Chandpur Port"))
        googleMap.addMarker(MarkerOptions().position(feni).title("Feni Port"))
        googleMap.addMarker(MarkerOptions().position(chattogram).title("Chattogram Port"))
        googleMap.addMarker(MarkerOptions().position(coxBazar).title("Cox's Bazar Port"))
        googleMap.addMarker(MarkerOptions().position(saintMartin).title("Saint Martin Port"))

        // Move camera to Dhaka initially
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 6f))
    }
}
