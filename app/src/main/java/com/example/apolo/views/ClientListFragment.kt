package com.example.apolo.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.adapters.ClientsListAdapter
import com.example.apolo.databinding.FragmentClientListBinding
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.GenericViewModel
import com.example.apolo.viewmodels.GenericViewModelFactory

class ClientListFragment : Fragment() {

    private lateinit var viewModel: GenericViewModel
    private val adapter by lazy{ ClientsListAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val binding = FragmentClientListBinding.inflate(layoutInflater, container, false)
        binding.list.adapter = adapter

        //ALTO ACOPLAMENTO
        val repository = Repository()

        val viewModelFactory = GenericViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GenericViewModel::class.java)

        viewModel.getClients()

        viewModel.clientsList.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful){
                Log.i("SUCCESS", "${response.body()}")
                response.body()?.let {
                    binding.tvQntEcs.text = "Quanditade de Clientes: ${it.size}"
                    adapter.setData(it)
                }
            }
            else{
                Log.i("FAIL", "${response.body()}")
            }
        })

        return binding.root
    }
}

