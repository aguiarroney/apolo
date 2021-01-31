package com.example.apolo.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.adapters.ListAdapter
import com.example.apolo.api.MockApi
import com.example.apolo.databinding.FragmentListBinding

class ListFragment : Fragment() {

    // USAR INJEÇÃO PARA ESSES OBJETOS
    private var adapter: RecyclerView.Adapter<ListAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListBinding.inflate(layoutInflater, container, false)
        //////// chamada de teste ---- remover
        MockApi().getClients()
        ///////
        adapter = ListAdapter()
        binding.list.adapter = adapter
        return binding.root
    }
}