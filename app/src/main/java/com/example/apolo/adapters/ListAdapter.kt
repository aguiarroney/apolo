package com.example.apolo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R
import com.example.apolo.models.Client

class ListAdapter() : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.tv_name)
        var itemAddress: TextView = itemView.findViewById(R.id.tv_address)
        var itemNexVisit: TextView = itemView.findViewById(R.id.tv_next_visit_text)
        var itemLastVisit: TextView = itemView.findViewById(R.id.tv_last_visit_text)
        var itemTvp: TextView = itemView.findViewById(R.id.tv_tvp_text)
    }

    private  var myClientsList= emptyList<Client>()

    override fun getItemCount() = myClientsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.itemName.text = myClientsList[position].name
        holder.itemAddress.text = myClientsList[position].address
        holder.itemNexVisit.text = myClientsList[position].nextVisit
        holder.itemLastVisit.text = myClientsList[position].lastVisit
        holder.itemTvp.text = myClientsList[position].tpv.toString()
    }

    fun setData(newList: List<Client>){
        myClientsList = newList
        notifyDataSetChanged()
    }

}