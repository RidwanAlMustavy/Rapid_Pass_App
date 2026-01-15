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

class ShoppingMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shopping_map)

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize map fragment
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        // Locations
        val wari = LatLng(23.7186, 90.4186)
        val lalbagh = LatLng(23.7189, 90.3889)
        val banani = LatLng(23.7936, 90.4043)
        val gulshan = LatLng(23.7806, 90.4128)
        val jamunaFuturePark = LatLng(23.8143, 90.4256)
        val mirpur10 = LatLng(23.8069, 90.3686)
        val bashundharaMall = LatLng(23.7509, 90.3909)
        val shantinagar = LatLng(23.7356, 90.4140)
        val badda = LatLng(23.7809, 90.4240)

        // Add markers
        googleMap.addMarker(MarkerOptions().position(wari).title("Sapnoo(Wari)"))
        googleMap.addMarker(MarkerOptions().position(lalbagh).title("Bigbazar(Lalbagh)"))
        googleMap.addMarker(MarkerOptions().position(banani).title("Lotto(Banani)"))
        googleMap.addMarker(MarkerOptions().position(gulshan).title("Gaget(Gulshan)"))
        googleMap.addMarker(MarkerOptions().position(jamunaFuturePark).title("Apple Store(Jamuna Future Park)"))
        googleMap.addMarker(MarkerOptions().position(mirpur10).title("Clothes Store(Mirpur 10)"))
        googleMap.addMarker(MarkerOptions().position(bashundharaMall).title("Raymond(Bashundhara City Mall)"))
        googleMap.addMarker(MarkerOptions().position(shantinagar).title("Bigbazar(Shantinagar)"))
        googleMap.addMarker(MarkerOptions().position(badda).title("Sapnoo(Badda)"))

        // Move camera to Dhaka (center view)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gulshan, 12f))
    }
}
