package com.example.apolo.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.databinding.FragmentDetailBinding
import com.example.apolo.models.Client
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.GenericViewModel
import com.example.apolo.viewmodels.GenericViewModelFactory
import com.google.android.gms.common.api.Api

class DetailFragment : Fragment() {

    private lateinit var viewModel: GenericViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(layoutInflater)
        val repository = Repository()
        val viewModelFactory = GenericViewModelFactory(repository)
        viewModel =
            ViewModelProvider(parentFragment!!, viewModelFactory).get(GenericViewModel::class.java)

        viewModel.marker.observe(viewLifecycleOwner, Observer { marker ->
            val client: Client = marker.tag as Client
            binding.tvMarkerName.text = client.name
            binding.tvMarkerAddress.text = client.address
            binding.tvMarkerSegment.text = client.segment
            binding.tvMarkerTpv.text = client.tpv.toString()
        })

        return binding.root
    }
}