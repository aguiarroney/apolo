package com.example.apolo.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.apolo.models.Client
import com.example.apolo.repository.Repository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class GenericViewModel(private val repository: Repository) : ViewModel() {

    var clientsList: MutableLiveData<retrofit2.Response<List<Client>>> = MutableLiveData()

    fun getClients(){
        viewModelScope.launch {
            val response = repository.getClients()
            clientsList.value = response
        }
    }


    fun setPins(mMap: GoogleMap, mList: List<Client>) {
        val bounds = LatLngBounds.builder()
        for (i in mList.indices) {
            val latLng = LatLng(mList[i].lat, mList[i].lng)
            bounds.include(latLng)
            if(mList[i].type == "lead"){
                mMap.addMarker(
                    MarkerOptions().position(latLng).title("Marker in ${mList[i].name}").icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                    )
                )
            }else{
                mMap.addMarker(
                    MarkerOptions().position(latLng).title("Marker in ${mList[i].name}").icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
                )
            }

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 1000, 1000, 100))

    }
}