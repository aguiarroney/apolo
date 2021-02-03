package com.example.apolo.views

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.R
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.GenericViewModel
import com.example.apolo.viewmodels.GenericViewModelFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlin.random.Random

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private val repository = Repository()
    private val viewModelFactory = GenericViewModelFactory(repository)
    private val viewModel: GenericViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_maps, container, false)

        viewModel.getPoloLimits()
        viewModel.getClients()
        viewModel.getLeads()

        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment?
        mapsFragment?.getMapAsync(this)
//        geraLatLng()
        return view
    }

    fun geraLatLng() {
        val randomValues = List(50) { Random.nextInt(1000, 9999) }
        Log.i("LATLANG", "$randomValues")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        viewModel.clientsList.observe(viewLifecycleOwner, Observer { response ->

            if (response.isSuccessful) {
                response.body()?.let { viewModel.setClientPins(mMap, it) }
            } else {
                Log.i("ERRO", "${response.code()}")
            }
        })

        viewModel.polo.observe(viewLifecycleOwner, Observer { response ->

            if (response.isSuccessful) {
                response.body()?.let { viewModel.drawLimits(mMap, it) }
            } else {
                Log.i("ERRO", "${response.code()}")
            }
        })

        viewModel.leadsList.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let { viewModel.setLeadPins(mMap, it) }
            } else {
                Log.i("ERRO", "${response.code()}")
            }
        })

        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker != null) {
            marker.showInfoWindow()
            viewModel.setMarker(marker)

            // ALTO ACOPLAMENTO
            val detail = DetailFragment()
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_detail, detail).commit()

        }
        return true
    }
}