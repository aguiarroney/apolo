package com.example.apolo.views

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.apolo.R
import com.example.apolo.adapters.ClientsListAdapter
import com.example.apolo.databinding.FragmentClientListBinding
import com.example.apolo.viewmodels.GenericViewModel

class ClientListFragment : Fragment() {

    private val viewModel: GenericViewModel by activityViewModels()
    private val adapter by lazy { ClientsListAdapter() }
    private lateinit var binding: FragmentClientListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientListBinding.inflate(layoutInflater, container, false)
        binding.list.adapter = adapter

        viewModel.fetchClients()

        viewModel.clientsList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                Log.i("SUCCESS", "${response.body()}")
                response.body()?.let {
                    binding.tvQntEcs.text = "Quanditade de Clientes: ${it.size}"
                    adapter.setData(it)
                }
            } else {
                Log.i("FAIL", "${response.body()}")
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.i_search)
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchString: String?): Boolean {
                adapter.filter.filter(searchString)
                return true
            }

            override fun onQueryTextChange(searchString: String?): Boolean {
                adapter.filter.filter(searchString)
                return true
            }

        })
    }
}

