package com.example.apolo.views

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.apolo.R
import com.example.apolo.databinding.FragmentMapsBinding
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
    private lateinit var binding: FragmentMapsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        viewModel.initMarkerList()
        viewModel.fetchClients()
        viewModel.fetchLeads()
        viewModel.fetchPoloLimits()

        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment?
        mapsFragment?.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //flitra os pins por TPV
        binding.btnClusterMax.setOnClickListener {
            setFilterLimits(0)
        }

        binding.btnClusterBetween.setOnClickListener {
            setFilterLimits(2)
        }

        //flitra os pins por TPV
        binding.btnClusterMin.setOnClickListener {
            setFilterLimits(1)
        }

        // Limpa todos os filtros de cluster
        binding.btnClearFilter.setOnClickListener {
            val markerList: ArrayList<Marker>? = viewModel.getMarkerList()

            var marker: Marker
            if (!markerList.isNullOrEmpty()) {
                for (i in markerList.indices) {
                    marker = markerList[i]
                    marker.isVisible = true
                }
            }

        }

        viewModel.clientsList.observe(viewLifecycleOwner, { response ->

            if (response.isSuccessful) {
                response.body()?.let {
                    viewModel.setClientPins(it)
                }
            } else {
                Log.i("ERRO", "${response.code()}")
            }
        })

        viewModel.polo.observe(viewLifecycleOwner, { response ->

            if (response.isSuccessful) {
                response.body()?.let { viewModel.drawLimits(mMap, it) }
            } else {
                Log.i("ERRO", "${response.code()}")
            }
        })

        viewModel.leadsList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    viewModel.setLeadPins(it)
                }
            } else {
                Log.i("ERRO", "${response.code()}")
            }
        })

        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapLongClickListener(this)
        viewModel.setMap(mMap)
    }

    private fun setFilterLimits(filter: Int) {
        val markerList: ArrayList<Marker>? = viewModel.getMarkerList()
        var marker: Marker
        var tpv = 0.0
        if (!markerList.isNullOrEmpty()) {
            for (i in markerList.indices) {
                marker = markerList[i]

                if (marker.tag != null) {
                    marker.isVisible = true
                    tpv = if (marker.tag is Client) {
                        val obj: Client = marker.tag as Client
                        obj.tpv
                    } else {
                        val obj: Lead = marker.tag as Lead
                        obj.tpv
                    }
                }

                if (tpv != null) {
                    Log.i("TPV Client", "$tpv")
                    when (filter) {
                        0 -> {
                            if (tpv <= 20000.0) {
                                marker.isVisible = false
                            }
                        }
                        1 -> {
                            if (tpv >= 10000.0) {
                                marker.isVisible = false
                            }
                        }
                        else -> {
                            if (tpv > 20000.0 || tpv < 10000.0) {
                                marker.isVisible = false
                            }
                        }
                    }
                }
            }
        }
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
        // melhorar este fluxo / instaciação feita apenas demonstrar o caso de uso
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Adicionar lead ou cliente?")
        builder.setPositiveButton("Cliente") { _: DialogInterface, _: Int ->
            val client = Client(
                "30",
                "Novo Cliente",
                "Rua Pedro Nolasco",
                10.0,
                "varejo",
                "12/12",
                "15/12",
                latLng!!.latitude,
                latLng.longitude,
                "Aprovada"
            )

            val marker = mMap.addMarker(
                MarkerOptions().position(LatLng(client.lat, client.lng))
                    .title("Cliente ${client.name}").icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                    )
            )
            marker.tag = client
            viewModel.addMarkerToList(marker)
        }
        builder.setNegativeButton("Lead") { _: DialogInterface, _: Int ->
            // instaciação feita apenas demonstrar o caso de uso
            val lead = Lead(
                "30",
                "Novo Restaurante",
                "Rua Pedro Nolasco",
                10.0,
                "varejo",
                "12/12",
                "15/12",
                latLng!!.latitude,
                latLng.longitude,
                true
            )

            val marker = mMap.addMarker(
                MarkerOptions().position(LatLng(lead.lat, lead.lng))
                    .title("Lead ${lead.name}").icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
            )
            marker.tag = lead
            viewModel.addMarkerToList(marker)
        }

        builder.show()
    }
}