package com.example.apolo.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.R
import com.example.apolo.adapters.ClientsListAdapter
import com.example.apolo.adapters.LeadsListAdapter
import com.example.apolo.databinding.FragmentLeadListBinding
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.GenericViewModel
import com.example.apolo.viewmodels.GenericViewModelFactory

class LeadListFragment : Fragment() {

    private lateinit var viewModel: GenericViewModel
    private val leadsAdapter by lazy{ LeadsListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLeadListBinding.inflate(layoutInflater, container, false)
        binding.listLeads.adapter = leadsAdapter
        val repository = Repository()
        val viewModelFactory = GenericViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(GenericViewModel::class.java)

        viewModel.getLeads()

        viewModel.leadsList.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful){
                response.body()?.let {
                    binding.tvQntLeads.text = "Quantidade de Leads: ${it.size}"
                    leadsAdapter.setData(it)
                }
            }
        })

        return binding.root
    }

}