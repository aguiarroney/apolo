package com.example.apolo.adapters

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R
import com.example.apolo.models.Client

class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val name: TextView = itemView.findViewById(R.id.tv_name)

    fun bind(item: Client, res: Resources){
        name.text = item.name
    }
}