package com.example.apolo.viewmodels

import android.graphics.Color
import android.text.BoringLayout
import android.util.Log
import androidx.lifecycle.*
import com.example.apolo.models.Client
import com.example.apolo.models.Lead
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
    var leadsList: MutableLiveData<Response<List<Lead>>> = MutableLiveData()
    private var _marker: MutableLiveData<Marker> = MutableLiveData()

    fun fetchClients() {
        Log.i("CHAMADA API", "CLIENT")
        viewModelScope.launch {
            val response = repository.fetchClients()
            clientsList.value = response
        }
    }

    fun fetchPoloLimits() {
        Log.i("CHAMADA API", "POLO")
        viewModelScope.launch {
            val response = repository.fetchPoloLimits()
            polo.value = response
        }
    }

    fun fetchLeads() {
        Log.i("CHAMADA API", "LEAD")
        viewModelScope.launch {
            val response = repository.fetchLeads()
            leadsList.value = response
        }
    }

    fun deleteClient(url: String) {
        viewModelScope.launch {
            val response = repository.deleteClient(url)
            if(response.isSuccessful){
                Log.i("CLIENTE", "DELETOU COM SUCESSO")
            }
            else{
                Log.i("CLIENTE", "falha: ${response.code()}")
            }
        }
    }

    fun deleteLead(url: String) {
        viewModelScope.launch {
            val response = repository.deleteLead(url)
            if(response.isSuccessful){
                Log.i("LEAD", "DELETOU COM SUCESSO")
            }
            else{
                Log.i("LEAD", "falha: ${response.code()}")
            }
        }
    }

//    fun postClient(){
//        viewModelScope.launch {
//            val response = repository.postClient()
//        }
//    }

    fun setClientPins(mMap: GoogleMap, mList: List<Client>, moveCamera: Boolean) {
        val bounds = LatLngBounds.builder()
        var marker: Marker
        for (i in mList.indices) {
            val latLng = LatLng(mList[i].lat, mList[i].lng)
            bounds.include(latLng)
            marker = mMap.addMarker(
                MarkerOptions().position(latLng).title("Cliente ${mList[i].id}").icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
            )
            marker.tag = mList[i]
        }
        if (!mList.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 1000, 1000, 100))
        }
    }

    fun setLeadPins(mMap: GoogleMap, mList: List<Lead>, moveCamera: Boolean) {
        val bounds = LatLngBounds.builder()
        var marker: Marker
        for (i in mList.indices) {
            val latLng = LatLng(mList[i].lat, mList[i].lng)
            bounds.include(latLng)
            marker = mMap.addMarker(
                MarkerOptions().position(latLng).title("Lead ${mList[i].name}").icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            marker.tag = mList[i]
        }
        if (!mList.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 1000, 1000, 100))
        }
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

    fun setMarker(mMarker: Marker) {
        _marker.value = mMarker
    }

    fun getMarker() = _marker.value
}