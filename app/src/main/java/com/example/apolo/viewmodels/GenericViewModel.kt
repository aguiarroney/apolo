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

    var clientsList: MutableLiveData<Response<List<Client>>> = MutableLiveData()
    var polo: MutableLiveData<Response<List<Polo>>> = MutableLiveData()
    var leadsList: MutableLiveData<Response<List<Lead>>> = MutableLiveData()
    private var _marker: MutableLiveData<Marker> = MutableLiveData()
    private var _map: MutableLiveData<GoogleMap> = MutableLiveData()
    private var _markerList: MutableLiveData<ArrayList<Marker>> = MutableLiveData()

    fun addMarkerToList(marker: Marker){

        if(_markerList.value == null)
            _markerList.value = ArrayList()

        _markerList.value!!.add(marker)
    }

    fun getMarkerList() = _markerList.value

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

    // função utilizada para deletar um pin do mapa e atualizar a clientes de leads através de uma chamada a API
    fun deleteClient(url: String) {
        viewModelScope.launch {
            val response = repository.deleteClient(url)
            if (response.isSuccessful) {
                Log.i("CLIENTE", "DELETOU COM SUCESSO")
            } else {
                Log.i("CLIENTE", "falha: ${response.code()}")
            }
        }
    }

    // função utilizada para deletar um pin do mapa e atualizar a lista de leads através de uma chamada a API
    fun deleteLead(url: String) {
        viewModelScope.launch {
            val response = repository.deleteLead(url)
            if (response.isSuccessful) {
                Log.i("LEAD", "DELETOU COM SUCESSO")
            } else {
                Log.i("LEAD", "falha: ${response.code()}")
            }
        }
    }

//     esta função foi criada inicialmente para ser utilizada nomomento da criação de um novo pin
//    porem o post na API utilizada gera valores de latitude e longitude aleatórios, causando um comportamento indevido na aplicação
//    fun postClient(){
//        viewModelScope.launch {
//            val response = repository.postClient()
//        }
//    }

    //função responsavel por criar os pins de clientes no mapa
    fun setClientPins(mList: List<Client>) {
        val bounds = LatLngBounds.builder()
        var marker: Marker
        for (i in mList.indices) {
            val latLng = LatLng(mList[i].lat, mList[i].lng)
            bounds.include(latLng)
            marker = _map.value!!.addMarker(
                MarkerOptions().position(latLng).title("Cliente ${mList[i].id}").icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
            )
            marker.tag = mList[i]
            addMarkerToList(marker)
        }
        if (!mList.isEmpty()) {
            _map.value!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 1000, 1000, 100))
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
            addMarkerToList(marker)
        }
        if (!mList.isEmpty()) {
            _map.value!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 1000, 1000, 100))
        }
    }

    // função responsavel por desenhar os limites do polo no mapa
    fun drawLimits(mMap: GoogleMap, limits: List<Polo>) {

        val latLngList = mutableListOf<LatLng>()

        for (i in limits.indices) {
            latLngList.add(LatLng(limits[i].lat, limits[i].lng))
        }

        val polygonOptions: PolygonOptions =
            PolygonOptions().addAll(latLngList).fillColor(0x1000FF00).strokeWidth(5f)
        val polygon = _map.value!!.addPolygon(polygonOptions)
        polygon.tag = "alpha"
    }

    //funções utilizadas pelo Fragment filho do MapsFragment responsavel por mostrar os detalhes dos pins

    fun setMarker(mMarker: Marker) {
        _marker.value = mMarker
    }

    fun getMarker() = _marker.value

    fun converPin(client: Client){
        _marker.value!!.remove()
        val marker = _map.value!!.addMarker(
            MarkerOptions().position(LatLng(client.lat, client.lng)).title("Cliente ${client.name}").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
        )
        marker.tag = client
        setMarker(marker)
        addMarkerToList(marker)
    }

    fun setMap(map: GoogleMap){
        _map.value = map
    }
}