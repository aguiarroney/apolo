package com.example.apolo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.apolo.databinding.FragmentDetailBinding
import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.example.apolo.viewmodels.GenericViewModel

class DetailFragment : Fragment() {

    private val viewModel: GenericViewModel by activityViewModels()
    private lateinit var binding : FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        viewModel.getMarker()?.let { marker ->

            when(marker.tag){
                is Client -> {
                    val obj:Client = marker.tag as Client
                    binding.tvType.text = "Cliente"
                    binding.tvMarkerName.text = obj .name
                    binding.tvMarkerAddress.text = obj.address
                    binding.tvMarkerSegment.text = obj.segment
                    binding.tvMarkerTpv.text = obj.tpv.toString()
                }
                is Lead -> {
                    val obj:Lead = marker.tag as Lead
                    binding.tvType.text = "Lead"
                    binding.tvMarkerName.text = obj .name
                    binding.tvMarkerAddress.text = obj.address
                    binding.tvMarkerSegment.text = obj.status
                    binding.tvMarkerTpv.text = obj.tpv.toString()
                }

            }

        }
        return binding.root
    }
}