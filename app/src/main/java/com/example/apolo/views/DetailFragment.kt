package com.example.apolo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.apolo.databinding.FragmentDetailBinding
import com.example.apolo.models.Client
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
            val client: Client = marker.tag as Client
            binding.tvMarkerName.text = client.name
            binding.tvMarkerAddress.text = client.address
            binding.tvMarkerSegment.text = client.segment
            binding.tvMarkerTpv.text = client.tpv.toString()
        }
        return binding.root
    }
}