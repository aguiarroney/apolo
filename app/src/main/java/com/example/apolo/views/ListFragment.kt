package com.example.apolo.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R
import com.example.apolo.adapters.ListAdapter

class ListFragment : Fragment() {

    // USAR INJEÇÃO PARA ESSES OBJETOS
    private var adapter: RecyclerView.Adapter<ListAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        adapter = ListAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)
        recyclerView.adapter = adapter

        return view
    }
}