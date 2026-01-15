package com.example.rapidpass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class BusMapActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    override fun onMapReady(googleMap: GoogleMap) {

        val kuril = LatLng(23.8223, 90.4271)
        val notunBazar = LatLng(23.7936, 90.4156)
        val badda = LatLng(23.7806, 90.4264)
        val rampura = LatLng(23.7586, 90.4280)
        val hatirjheel = LatLng(23.7639, 90.4210)

        googleMap.addMarker(MarkerOptions().position(kuril).title("Kuril Bus Stop"))
        googleMap.addMarker(MarkerOptions().position(notunBazar).title("Notun Bazar Bus Stop"))
        googleMap.addMarker(MarkerOptions().position(badda).title("Badda Bus Stop"))
        googleMap.addMarker(MarkerOptions().position(rampura).title("Rampura Bus Stop"))
        googleMap.addMarker(MarkerOptions().position(hatirjheel).title("Hatirjheel Bus Stop"))

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kuril, 12f))
    }

}
