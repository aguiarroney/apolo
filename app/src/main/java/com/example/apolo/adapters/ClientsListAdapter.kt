package com.example.apolo.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R
import com.example.apolo.models.Client
import java.time.LocalDate

class ClientsListAdapter() : RecyclerView.Adapter<ClientsListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.tv_name)
        var itemAddress: TextView = itemView.findViewById(R.id.tv_address)
        var itemNexVisit: TextView = itemView.findViewById(R.id.tv_next_visit_text)
        var itemLastVisit: TextView = itemView.findViewById(R.id.tv_last_visit_text)
        var itemtpv: TextView = itemView.findViewById(R.id.tv_tpv_text)
        var itemSatsfaction: TextView = itemView.findViewById(R.id.tv_client_satsfaction)
    }

    private var myClientsList = emptyList<Client>()

    override fun getItemCount() = myClientsList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClientsListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ClientsListAdapter.ViewHolder, position: Int) {
        holder.itemName.text = myClientsList[position].name
        holder.itemAddress.text = myClientsList[position].address
        holder.itemNexVisit.text = myClientsList[position].nextVisit
        holder.itemLastVisit.text = "há " + setLastVisit(myClientsList[position].lastVisit).toString()
        holder.itemtpv.text = myClientsList[position].tpv.toString()
        holder.itemSatsfaction.text = myClientsList[position].satisfaction
    }

    fun setData(newList: List<Client>) {
        myClientsList = newList
        notifyDataSetChanged()
    }

    private fun getDaysInMonth(month: Int): Int {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) return 31
        else if (month == 4 || month == 6 || month == 9 || month == 11) return 30
        else return 28
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setLastVisit(nextVisit: String): Int {
        val now = LocalDate.now()
        val dayNow = now.dayOfMonth
        val monthNow = now.monthValue
        val lastMonth = nextVisit.substring(3, 5).toInt()
        val lastDay = nextVisit.substring(0, 2).toInt()
        var days = 0

        if(lastMonth == monthNow){
            days = dayNow - lastDay
        }
        else if(lastMonth < monthNow){
            if (lastDay > dayNow)
                days = (getDaysInMonth(lastMonth) - lastDay) + dayNow
            else
                days = dayNow - lastDay
        }

        return (days )
    }

}