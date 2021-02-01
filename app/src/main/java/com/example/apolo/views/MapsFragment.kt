package com.example.apolo.views

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: GenericViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_maps, container, false)

        val repository = Repository()
        val viewModelFactory = GenericViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GenericViewModel::class.java)

        viewModel.getClients()

        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment?
        mapsFragment?.getMapAsync(this)
        return view
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        viewModel.clientsList.observe(viewLifecycleOwner, Observer { response ->

            if(response.isSuccessful){
                response.body()?.let { viewModel.setPins(mMap, it) }
            }
            else{
                Log.i("ERRO", "${response.code()}")
            }
        })
    }
}