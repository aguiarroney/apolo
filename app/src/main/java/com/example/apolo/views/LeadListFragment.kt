package com.example.apolo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.R
import com.example.apolo.adapters.LeadsListAdapter
import com.example.apolo.databinding.FragmentLeadListBinding
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.*

class LeadListFragment : Fragment() {

    private val leadsAdapter by lazy { LeadsListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLeadListBinding.inflate(layoutInflater, container, false)
        binding.listLeads.adapter = leadsAdapter

        val repository = Repository()
        val factory = LeadViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory).get(LeadViewModel::class.java)

        viewModel.fetchLeads()

        viewModel.leadsList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    binding.tvQntLeads.text = requireContext().getString(R.string.qnt_leads, it.size)
                    leadsAdapter.setData(it)
                }
            }
        })

        return binding.root
    }

}