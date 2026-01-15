package com.example.rapidpass

import android.os.Bundle
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

class ParkingMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_parking_map)

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize map
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        // Parking locations
        val setuBhaban = LatLng(23.7723, 90.3654)
        val jamunaFuturePark = LatLng(23.8147, 90.4247)
        val bashundharaMall = LatLng(23.8191, 90.4526)
        val mohakhali = LatLng(23.7781, 90.3972)
        val gulistan = LatLng(23.7231, 90.4086)
        val saidabad = LatLng(23.7099, 90.4253)
        val banani = LatLng(23.7936, 90.4043)
        val ramna = LatLng(23.7385, 90.3958)

        // Add markers
        googleMap.addMarker(MarkerOptions().position(setuBhaban).title("Setu Bhaban Parking"))
        googleMap.addMarker(MarkerOptions().position(jamunaFuturePark).title("Jamuna Future Park Parking"))
        googleMap.addMarker(MarkerOptions().position(bashundharaMall).title("Bashundhara Shopping Mall Parking"))
        googleMap.addMarker(MarkerOptions().position(mohakhali).title("Mohakhali Parking"))
        googleMap.addMarker(MarkerOptions().position(gulistan).title("Gulistan Parking"))
        googleMap.addMarker(MarkerOptions().position(saidabad).title("Saidabad Parking"))
        googleMap.addMarker(MarkerOptions().position(banani).title("Banani Parking"))
        googleMap.addMarker(MarkerOptions().position(ramna).title("Ramna Parking"))

        // Move camera to Dhaka (center)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mohakhali, 12f))
    }
}
