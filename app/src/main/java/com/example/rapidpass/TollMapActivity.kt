package com.example.rapidpass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class TollMapActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toll_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // ✅ Toll locations
        val farmgate = LatLng(23.7465, 90.3940)
        val banani = LatLng(23.7800, 90.4200)
        val nikunjo = LatLng(23.7930, 90.4130)
        val airport = LatLng(23.8430, 90.3970)
        val uttora = LatLng(23.8740, 90.3850)

        // ✅ Add markers
        googleMap.addMarker(MarkerOptions().position(farmgate).title("Farmgate Toll"))
        googleMap.addMarker(MarkerOptions().position(banani).title("Banani Toll"))
        googleMap.addMarker(MarkerOptions().position(nikunjo).title("Nikunjo Toll"))
        googleMap.addMarker(MarkerOptions().position(airport).title("Airport Toll"))
        googleMap.addMarker(MarkerOptions().position(uttora).title("Uttora Toll"))

        // ✅ Move camera to first location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(farmgate, 12f))
    }
}
