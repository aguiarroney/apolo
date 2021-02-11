package com.example.apolo.adapters

import com.example.apolo.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.models.Lead

class LeadsListAdapter : RecyclerView.Adapter<LeadsListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.tv_lead_name)
        var itemAddress: TextView = itemView.findViewById(R.id.tv_lead_address)
        var itemNexVisit: TextView = itemView.findViewById(R.id.tv_lead_next_visit_text)
        var itemVisitQnt: TextView = itemView.findViewById(R.id.tv_lead_visits_qnt)
        var itemtpv: TextView = itemView.findViewById(R.id.tv_lead_tpv_text)
        var itemStatus: TextView = itemView.findViewById(R.id.tv_status)
        var itemProposta: TextView = itemView.findViewById(R.id.tv_proposta)
        var itemBtnProposta: TextView = itemView.findViewById(R.id.btn_proposta)
    }

    private var myLeadsList = emptyList<Lead>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_leads_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = myLeadsList[position].name
        holder.itemAddress.text = myLeadsList[position].address
        holder.itemNexVisit.text = myLeadsList[position].nextVisit
        holder.itemVisitQnt.text = holder.itemView.context.getString(R.string.visitas, myLeadsList[position].visitQnt)
        holder.itemtpv.text = myLeadsList[position].tpv.toString()
        holder.itemStatus.text = myLeadsList[position].status
        if(myLeadsList[position].proposta){
            holder.itemProposta.isVisible = true
            holder.itemBtnProposta.isVisible = false
        }
        else{
            holder.itemProposta.isVisible = false
            holder.itemBtnProposta.isVisible = true
        }

    }

    override fun getItemCount(): Int = myLeadsList.size

    fun setData(newList: List<Lead>) {
        myLeadsList = newList
        notifyDataSetChanged()
    }
}