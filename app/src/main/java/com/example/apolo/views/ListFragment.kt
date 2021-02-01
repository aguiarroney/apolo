package com.example.apolo.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.adapters.ListAdapter
import com.example.apolo.databinding.FragmentListBinding
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.GenericViewModel
import com.example.apolo.viewmodels.GenericViewModelFactory

class ListFragment : Fragment() {

    private lateinit var viewModel: GenericViewModel
    private val adapter by lazy{ ListAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val binding = FragmentListBinding.inflate(layoutInflater, container, false)
        binding.list.adapter = adapter

        val repository = Repository()

        val viewModelFactory = GenericViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GenericViewModel::class.java)

        viewModel.getClients()

        viewModel.clientsList.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful){
                Log.i("SUCCESS", "${response.body()}")
                response.body()?.let { adapter.setData(it) }
            }
            else{
                Log.i("FAIL", "${response.body()}")
            }
        })

        return binding.root
    }
}

