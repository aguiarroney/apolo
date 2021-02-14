package com.example.apolo.viewmodels

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

    private var _clientsList: MutableLiveData<Response<List<Client>>?> = MutableLiveData()
    val clientList: LiveData<Response<List<Client>>?> = _clientsList

    private var _leadsList: MutableLiveData<Response<List<Lead>>?> = MutableLiveData()
    val leadList: LiveData<Response<List<Lead>>?> = _leadsList

    var polo: MutableLiveData<Response<List<Polo>>> = MutableLiveData()
    private var _marker: MutableLiveData<Marker> = MutableLiveData()
    private var _map: MutableLiveData<GoogleMap> = MutableLiveData()
    private var _markerList: MutableLiveData<ArrayList<Marker>> = MutableLiveData()

    private var _routeList: MutableLiveData<ArrayList<Marker>?> = MutableLiveData()

    private fun resetRouteList() {
        _routeList.value = null
    }

    private fun addRouteList(marker: Marker) {
        if (_routeList.value.isNullOrEmpty())
            _routeList.value = ArrayList()

        _routeList.value!!.add(marker)
    }

    fun resetClientLiveData() {
        _clientsList.value = null
    }

    fun resetLeadLiveData() {
        _leadsList.value = null
    }

    fun addMarkerToList(marker: Marker) {

        if (_markerList.value == null)
            _markerList.value = ArrayList()

        _markerList.value!!.add(marker)
    }

    fun getMarkerList() = _markerList.value

    fun initMarkerList() {
        _markerList.value = ArrayList()
    }

    fun fetchClients() {
        viewModelScope.launch {
            val response = repository.fetchClients()
            if (response != null) {
                if (response.isSuccessful) {
                    Log.i("OBS CLIENTS API", "setou")
                    _clientsList.value = response
                } else {
                    Log.i("ERRO CLIENTS API", "${response.code()}")
                }
            }
        }
    }

    fun fetchPoloLimits() {
        viewModelScope.launch {
            val response = repository.fetchPoloLimits()
            if (response != null) {
                if (response.isSuccessful)
                    polo.value = response
                else {
                    Log.i("ERRO POLO API", "${response.code()}")
                }
            }
        }
    }

    fun fetchLeads() {
        viewModelScope.launch {
            val response = repository.fetchLeads()
            if (response != null) {
                if (response.isSuccessful) {
                    _leadsList.value = response
                    Log.i("OBS LEADAS API", "setou")
                } else {
                    Log.i("ERRO LEADS API", "${response.code()}")
                }
            }
        }
    }

    // função utilizada para deletar um pin do mapa e atualizar a clientes de leads através de uma chamada a API
    fun deleteClient(url: String) {
        viewModelScope.launch {
            val response = repository.deleteClient(url)
            if (response != null) {
                if (response.isSuccessful) {
                    Log.i("CLIENTE", "DELETOU COM SUCESSO")
                } else {
                    Log.i("CLIENTE", "falha: ${response.code()}")
                }
            }
        }
    }

    // função utilizada para deletar um pin do mapa e atualizar a lista de leads através de uma chamada a API
    fun deleteLead(url: String) {
        viewModelScope.launch {
            val response = repository.deleteLead(url)
            if (response != null) {
                if (response.isSuccessful) {
                    Log.i("LEAD", "DELETOU COM SUCESSO")
                } else {
                    Log.i("LEAD", "falha: ${response.code()}")
                }
            }
        }
    }

    //função responsavel por criar os pins de clientes no mapa
    fun setClientPins(mList: List<Client>) {
        val bounds = LatLngBounds.builder()
        var marker: Marker
        for (i in mList.indices) {
            val latLng = LatLng(mList[i].lat, mList[i].lng)
            bounds.include(latLng)
            marker = _map.value!!.addMarker(
                MarkerOptions().position(latLng).title("Cliente ${mList[i].name}").icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
            )
            marker.tag = mList[i]
            if (!_routeList.value.isNullOrEmpty()) {
                setRouteItem(marker)
            }
            addMarkerToList(marker)
        }
        if (mList.isNotEmpty()) {
            _map.value!!.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds.build(),
                    1000,
                    1000,
                    100
                )
            )
        }
    }

    // função responsavel por criar os pins de leads no mapa
    fun setLeadPins(mList: List<Lead>) {
        val bounds = LatLngBounds.builder()
        var marker: Marker
        for (i in mList.indices) {
            val latLng = LatLng(mList[i].lat, mList[i].lng)
            bounds.include(latLng)
            marker = _map.value!!.addMarker(
                MarkerOptions().position(latLng).title("Lead ${mList[i].name}").icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            marker.tag = mList[i]
            if (!_routeList.value.isNullOrEmpty()) {
                setRouteItem(marker)
            }
            addMarkerToList(marker)
        }
        if (mList.isNotEmpty()) {
            _map.value!!.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds.build(),
                    1000,
                    1000,
                    100
                )
            )
        }
    }

    private fun setRouteItem(marker: Marker) {
        for (j in _routeList.value!!.indices) {
            if (_routeList.value!![j].tag == marker.tag) {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            }
        }
    }

    // função responsavel por desenhar os limites do polo no mapa
    fun drawLimits(limits: List<Polo>) {

        val latLngList = mutableListOf<LatLng>()

        for (i in limits.indices) {
            latLngList.add(LatLng(limits[i].lat, limits[i].lng))
        }

        if (latLngList.isNotEmpty()) {
            val polygonOptions: PolygonOptions =
                PolygonOptions().addAll(latLngList).fillColor(0x1000FF00).strokeWidth(5f)
            val polygon = _map.value!!.addPolygon(polygonOptions)
            polygon.tag = "alpha"
        }
    }

    //funções utilizadas pelo Fragment filho do MapsFragment responsavel por mostrar os detalhes dos pins

    fun setMarker(mMarker: Marker) {
        _marker.value = mMarker
    }

    fun getMarker() = _marker.value

    fun convertPin(client: Client) {
        _marker.value!!.remove()
        val marker = _map.value!!.addMarker(
            MarkerOptions().position(LatLng(client.lat, client.lng)).title("Cliente ${client.name}")
                .icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
        )
        marker.tag = client
        setMarker(marker)
        addMarkerToList(marker)
    }

    fun setMap(map: GoogleMap) {
        _map.value = map
    }

    // funções para manipular rotas

    fun route(position: Int) {

        val markerList = _markerList
        val marker = markerList.value?.get(position)
        addRouteList(marker!!)
    }

    fun clearRoute(){
        resetRouteList()
        for (i in _markerList.value!!.indices){
            when(_markerList.value!![i].tag){
                is Client ->{
                    _markerList.value!![i].setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                }
                else -> {
                    _markerList.value!![i].setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                }
            }
        }
    }
}