package com.example.apolo.viewmodels

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragmentViewModel : ViewModel() {

    fun setPins(mMap: GoogleMap, latlgn: LatLng) {
        mMap.addMarker(MarkerOptions().position(latlgn).title("Marker in rio"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlgn, 15f))
    }
}