package com.example.apolo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R
import com.example.apolo.R.id
import com.example.apolo.R.layout
import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.google.android.gms.maps.model.Marker

class RouteListAdapter(private val listener: OnButtonClickListener) :
    RecyclerView.Adapter<RouteListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var itemName: TextView = itemView.findViewById(id.tv_route_item_title)
        var itemAddress: TextView = itemView.findViewById(id.tv_route_item_address)
        private var itemBtn: Button = itemView.findViewById(id.btn_add_to_route)

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
        if (_myRouteItems[position].tag != null) {
            when (_myRouteItems[position].tag) {
                is Client -> {
                    val obj: Client = _myRouteItems[position].tag as Client
                    holder.itemName.text =
                        holder.itemView.context.getString(R.string.cliente_title, obj.name)
                    holder.itemAddress.text = obj.address
                }
                else -> {
                    val obj: Lead = _myRouteItems[position].tag as Lead
                    holder.itemName.text =
                        holder.itemView.context.getString(R.string.lead_title, obj.name)
                    holder.itemAddress.text = obj.address
                }
            }
        }
    }

    override fun getItemCount() = _myRouteItems.size

    fun setData(markerList: ArrayList<Marker>) {
        _myRouteItems = markerList
        notifyDataSetChanged()
    }

    interface OnButtonClickListener {
        fun onButtonClick(position: Int)
    }
}