package com.example.apolo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.apolo.R
import com.example.apolo.adapters.LeadsListAdapter
import com.example.apolo.databinding.FragmentLeadListBinding
import com.example.apolo.viewmodels.GenericViewModel

class LeadListFragment : Fragment() {

    private val viewModel: GenericViewModel by activityViewModels()
    private val leadsAdapter by lazy { LeadsListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLeadListBinding.inflate(layoutInflater, container, false)
        binding.listLeads.adapter = leadsAdapter
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