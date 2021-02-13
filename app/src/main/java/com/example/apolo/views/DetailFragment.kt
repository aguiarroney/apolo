package com.example.apolo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.R
import com.example.apolo.databinding.FragmentDetailBinding
import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.GenericViewModel
import com.example.apolo.viewmodels.GenericViewModelFactory

class DetailFragment : Fragment() {

    private val viewModel: GenericViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        binding.btnConvert.isVisible = false

        // a funcionalidade de conversão funcionará melhor com uma API verdadeira capaz de fazer posts e updates
        // o código aqui implementado é apenas uma representação do efeito visual dessa ação no app
        binding.btnConvert.setOnClickListener {
            viewModel.getMarker()?.let { marker ->
                when(marker.tag){
                    is Lead ->{
                        val lead: Lead = marker.tag as Lead
                        val client = Client(lead.id, lead.name, lead.address, lead.tpv, "", "", lead.nextVisit, lead.lat, lead.lng, "Satisfeito")
                        viewModel.converPin(client)
                    }
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.getMarker()?.let { marker ->
                when (marker.tag) {
                    is Client -> {
                        val obj: Client = marker.tag as Client
                        viewModel.deleteClient("clients/${obj.id}")
                    }
                    is Lead -> {
                        val obj: Lead = marker.tag as Lead
                        viewModel.deleteLead("leads/${obj.id}")
                    }
                }
                marker.remove()
            }
        }

        //preenche os campos da tela de detalhes
        viewModel.getMarker()?.let { marker ->

            when (marker.tag) {
                is Client -> {
                    val obj: Client = marker.tag as Client
                    binding.tvType.text = getString(R.string.cliente,obj.id)
                    binding.tvMarkerName.text = obj.name
                    binding.tvMarkerAddress.text = obj.address
                    binding.tvMarkerSegment.text = obj.segment
                    binding.tvMarkerTpv.text = obj.tpv.toString()
                }
                is Lead -> {
                    val obj: Lead = marker.tag as Lead
                    binding.tvType.text =getString(R.string.lead,obj.id)
                    binding.tvMarkerName.text = obj.name
                    binding.tvMarkerAddress.text = obj.address
                    binding.tvMarkerSegment.text = obj.status
                    binding.tvMarkerTpv.text = obj.tpv.toString()
                    binding.btnConvert.isVisible = true
                }

            }

        }
        return binding.root
    }
}