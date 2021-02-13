package com.example.apolo.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.apolo.adapters.ClientsListAdapter
import com.example.apolo.adapters.RouteListAdapter
import com.example.apolo.databinding.FragmentRouteListBinding
import com.example.apolo.viewmodels.GenericViewModel

class RouteFragment : Fragment(), RouteListAdapter.onButtonClickListener {

    private val viewModel: GenericViewModel by activityViewModels()
    private val myAdapter by lazy { RouteListAdapter(this) }
    private lateinit var binding: FragmentRouteListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRouteListBinding.inflate(layoutInflater, container, false)
        binding.rvRouteItems.adapter = myAdapter

        viewModel.getMarkerList()?.let {
            binding.tvRoutesInfo.text = "Items: ${it.size}"
            myAdapter.setData(it)
        }

        return binding.root
    }

    override fun onButtonClick(position: Int) {
        viewModel.route(position)
    }

}