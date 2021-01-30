package com.example.apolo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R
import com.example.apolo.models.Client

// TODO RECEBER A LISTA DE CLIENTES VIA CONSTRUTOR
class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemName: TextView = itemView.findViewById(R.id.tv_name)
        var itemAddress: TextView = itemView.findViewById(R.id.tv_address)
        var itemNexVisit: TextView = itemView.findViewById(R.id.tv_next_visit_text)
        var itemLastVisit: TextView = itemView.findViewById(R.id.tv_last_visit_text)
        var itemTvp: TextView = itemView.findViewById(R.id.tv_tvp_text)
    }

    // LISTA APENAS PARA TESTE
    val data = listOf<Client>(Client(1, "cliente", "Mercado Compre Bem", "Rua Gomes Carneiro, 56", 100.0, "varejo", "12/12/2020", "12/01/2021"),
        Client(2, "lead", "Loja da Raquel", "Rua Gomes Carneiro, 56", 100.0, "varejo", "12/12/2020", "12/01/2021"),
        Client(3, "cliente", "Cantina da praça", "Rua Gomes Carneiro, 56", 100.0, "varejo", "12/12/2020", "12/01/2021"),
        )

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.itemName.text = data[position].name
        holder.itemAddress.text = data[position].address
        holder.itemNexVisit.text = data[position].nextVisit
        holder.itemLastVisit.text = data[position].lastVisit
        holder.itemTvp.text = data[position].tpv.toString()
    }



}