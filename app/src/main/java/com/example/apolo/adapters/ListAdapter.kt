package com.example.apolo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R
import com.example.apolo.models.Client

// RECEBER A LISTA DE CLIENTES VIA CONSTRUTOR
class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemName: TextView = itemView.findViewById(R.id.tv_name)
    }

    // LISTA APENAS PARA TESTE
    val data = listOf<Client>(Client("Cliente 1"), Client("Cliente 2"), Client("Cliente 3"))

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.itemName.text = data[position].name
    }



}