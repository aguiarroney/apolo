package com.example.apolo.views

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.apolo.R
import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.GenericViewModel
import com.example.apolo.viewmodels.GenericViewModelFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private val repository = Repository()
    private val viewModelFactory = GenericViewModelFactory(repository)
    private val viewModel: GenericViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_maps, container, false)

        viewModel.fetchClients()
        viewModel.fetchLeads()
        viewModel.fetchPoloLimits()

        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment?
        mapsFragment?.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        viewModel.clientsList.observe(viewLifecycleOwner, Observer { response ->

            if (response.isSuccessful) {
                response.body()?.let {
                    viewModel.setClientPins(mMap, it, true)
                }
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
                response.body()?.let {
                    viewModel.setLeadPins(mMap, it, true)
                }
            } else {
                Log.i("ERRO", "${response.code()}")
            }
        })

        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapLongClickListener(this)
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

    // a funcionalidade de adicionar um pin no mapa funcionará melhor com uma API verdadeira capaz de fazer posts e updates
    // o código aqui implementado é apenas uma representação do efeito visual dessa ação no app

    override fun onMapLongClick(latLng: LatLng?) {
        //TODO melhorar este fluxo / instaciação feita apenas para teste

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Adicionar cliente ou pin?")
        builder.setPositiveButton("Cliente") { dialogInterface: DialogInterface, i: Int ->
            //TODO melhorar este fluxo / instaciação feita apenas para teste
            val client = Client(
                "30",
                "Novo Cliente",
                "Rua Pedro Nolasco",
                10.0,
                "varejo",
                "12/12",
                "15/12",
                latLng!!.latitude,
                latLng!!.longitude,
                "Aprovada"
            )

            val marker = mMap.addMarker(
                MarkerOptions().position(LatLng(client.lat, client.lng))
                    .title("Cliente ${client.id}").icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
            )
            marker.tag = client
        }
        builder.setNegativeButton("Lead") { dialogInterface: DialogInterface, i: Int ->
            val lead = Lead(
                "30",
                "Novo Cliente",
                "Rua Pedro Nolasco",
                10.0,
                "varejo",
                "12/12",
                "15/12",
                latLng!!.latitude,
                latLng!!.longitude,
            )

            val marker = mMap.addMarker(
                MarkerOptions().position(LatLng(lead.lat, lead.lng))
                    .title("Lead ${lead.id}").icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
            )
            marker.tag = lead
        }

        builder.show()
    }
}