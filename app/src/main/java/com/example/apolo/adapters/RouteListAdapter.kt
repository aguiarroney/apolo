package com.example.apolo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R.*
import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.google.android.gms.common.api.Api
import com.google.android.gms.maps.model.Marker

class RouteListAdapter: RecyclerView.Adapter<RouteListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(id.tv_route_item_title)

    }
    private var _myRouteItems = ArrayList<Marker>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteListAdapter.ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(layout.fragment_routes_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(_myRouteItems[position].tag){
            is Client ->{
                val obj: Client = _myRouteItems[position].tag as Client
                holder.itemName.text = "Cliente ${obj.name}"
            }
            else ->{
                val obj: Lead = _myRouteItems[position].tag as Lead
                holder.itemName.text = "Lead ${obj.name}"
            }
        }


    }

    override fun getItemCount() = _myRouteItems.size

    fun setData(markerList: ArrayList<Marker>){
        _myRouteItems = markerList
        notifyDataSetChanged()
    }
}