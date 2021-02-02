package com.example.apolo.viewmodels

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.*
import com.example.apolo.models.Client
import com.example.apolo.models.Polo
import com.example.apolo.repository.Repository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.launch
import retrofit2.Response

class GenericViewModel(private val repository: Repository) : ViewModel() {

    var clientsList: MutableLiveData<Response<List<Client>>> = MutableLiveData()
    var polo: MutableLiveData<Response<List<Polo>>> = MutableLiveData()
    var marker: MutableLiveData<Marker> = MutableLiveData()

    fun getClients() {
        viewModelScope.launch {
            val response = repository.getClients()
            clientsList.value = response
        }
    }

    fun getPoloLimits() {
        viewModelScope.launch {
            val response = repository.getPoloLimits()
            polo.value = response
        }
    }

    fun setPins(mMap: GoogleMap, mList: List<Client>) {
        val bounds = LatLngBounds.builder()
        var marker: Marker
        for (i in mList.indices) {
            val latLng = LatLng(mList[i].lat, mList[i].lng)
            bounds.include(latLng)
            if (mList[i].type == "lead") {
                marker = mMap.addMarker(
                    MarkerOptions().position(latLng).title("Lead ${mList[i].name}").icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                    )
                )
            } else {
                marker = mMap.addMarker(
                    MarkerOptions().position(latLng).title("Cliente ${mList[i].name}").icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
                )
            }
            marker.tag = mList[i]
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 1000, 1000, 100))
    }

    fun drawLimits(mMap: GoogleMap, limits: List<Polo>) {

        val latLngList = mutableListOf<LatLng>()

        for (i in limits.indices) {
            latLngList.add(LatLng(limits[i].lat, limits[i].lng))
        }

        val polygonOptions: PolygonOptions =
            PolygonOptions().addAll(latLngList).fillColor(0x1000FF00).strokeWidth(5f)
        val polygon = mMap.addPolygon(polygonOptions)
        polygon.tag = "alpha"
    }

    fun setDetails(mMarker: Marker) {
        marker.value = mMarker
    }
}