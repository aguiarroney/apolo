package com.example.apolo.views

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
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

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

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

        viewModel.getPoloLimits()
        viewModel.getClients()

        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment?
        mapsFragment?.getMapAsync(this)

        val detail = DetailFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_detail, detail).commit()

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        viewModel.clientsList.observe(viewLifecycleOwner, Observer { response ->

            if (response.isSuccessful) {
                response.body()?.let { viewModel.setPins(mMap, it) }
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

        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker != null) {
            viewModel.setDetails(marker)
        }
        return true
    }
}