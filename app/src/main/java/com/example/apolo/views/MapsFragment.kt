package com.example.apolo.views

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.R
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.MapsFragmentViewModel

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: MapsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_maps, container, false)

        val rep = Repository()
        val mapsViewModelFactory = MapsFragmentViewModelFactory(rep)
        viewModel =
            ViewModelProvider(this, mapsViewModelFactory).get(MapsFragmentViewModel::class.java)

        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment?
        mapsFragment?.getMapAsync(this)
        return view
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        viewModel.setPins(mMap)
    }
}