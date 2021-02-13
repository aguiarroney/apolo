package com.example.apolo.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R
import com.example.apolo.R.*
import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.google.android.gms.common.api.Api
import com.google.android.gms.maps.model.Marker

class RouteListAdapter(private val listener: onButtonClickListener) :
    RecyclerView.Adapter<RouteListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var itemName: TextView = itemView.findViewById(R.id.tv_route_item_title)
        var itemAddress: TextView = itemView.findViewById(R.id.tv_route_item_address)
        var itemBtn: Button = itemView.findViewById(R.id.btn_add_to_route)

        init {
            itemBtn.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.onButtonClick(position)
        }
    }

    private var _myRouteItems = ArrayList<Marker>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(layout.fragment_routes_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (_myRouteItems[position].tag) {
            is Client -> {
                val obj: Client = _myRouteItems[position].tag as Client
                holder.itemName.text = "Cliente ${obj.name}"
                holder.itemAddress.text = "${obj.address}"
            }
            else -> {
                val obj: Lead = _myRouteItems[position].tag as Lead
                holder.itemName.text = "Lead ${obj.name}"
                holder.itemAddress.text = "${obj.address}"
            }
        }
    }

    override fun getItemCount() = _myRouteItems.size

    fun setData(markerList: ArrayList<Marker>) {
        _myRouteItems = markerList
        notifyDataSetChanged()
    }

    interface onButtonClickListener {
        fun onButtonClick(position: Int)
    }
}