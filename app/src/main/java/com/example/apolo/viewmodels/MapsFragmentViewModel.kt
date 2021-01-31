package com.example.apolo.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolo.models.Client
import com.example.apolo.repository.Repository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class MapsFragmentViewModel(private val repository: Repository) : ViewModel() {

    private lateinit var clientsList: List<Client>


    fun setPins(mMap: GoogleMap) {

        viewModelScope.launch {
            val bounds = LatLngBounds.builder()
            val res = repository.getClients()
            if (res.isSuccessful) {
                clientsList = res.body()!!
                Log.i("MAPS", "${clientsList}")
            } else {
                Log.i("MAPS FAILED", "${res.code()}")
            }

            for (i in clientsList.indices) {
                val latLng = LatLng(clientsList[i].lat, clientsList[i].lng)
                bounds.include(latLng)
                mMap.addMarker(
                    MarkerOptions().position(latLng).title("Marker in ${clientsList[i].name}")
                )
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 1000, 1000, 100))
        }


    }
}