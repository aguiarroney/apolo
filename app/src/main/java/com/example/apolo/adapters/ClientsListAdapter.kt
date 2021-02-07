package com.example.apolo.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.apolo.R
import com.example.apolo.models.Client
import java.time.LocalDate

class ClientsListAdapter() : RecyclerView.Adapter<ClientsListAdapter.ViewHolder>(), Filterable {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.tv_name)
        var itemAddress: TextView = itemView.findViewById(R.id.tv_address)
        var itemNexVisit: TextView = itemView.findViewById(R.id.tv_next_visit_text)
        var itemLastVisit: TextView = itemView.findViewById(R.id.tv_last_visit_text)
        var itemtpv: TextView = itemView.findViewById(R.id.tv_tpv_text)
        var itemSatsfaction: TextView = itemView.findViewById(R.id.tv_client_satsfaction)
    }

    private var _myClientsList = ArrayList<Client>()
    private var _myClientsListFiltered = ArrayList<Client>()
    override fun getItemCount() = _myClientsList.size

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
        holder.itemName.text = _myClientsList[position].name
        holder.itemAddress.text = _myClientsList[position].address
        holder.itemNexVisit.text = _myClientsList[position].nextVisit
        holder.itemLastVisit.text =
            "há " + setLastVisit(_myClientsList[position].lastVisit).toString()
        holder.itemtpv.text = _myClientsList[position].tpv.toString()
        holder.itemSatsfaction.text = _myClientsList[position].satisfaction
    }

    fun setData(newList: List<Client>) {
        _myClientsListFiltered.clear()
        _myClientsList.clear()
        _myClientsList.addAll(newList as ArrayList<Client>)
        _myClientsListFiltered.addAll(newList as ArrayList<Client>)
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

        if (lastMonth == monthNow) {
            days = dayNow - lastDay
        } else if (lastMonth < monthNow) {
            if (lastDay > dayNow)
                days = (getDaysInMonth(lastMonth) - lastDay) + dayNow
            else
                days = dayNow - lastDay
        }

        return (days)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val localList = ArrayList<Client>()
                if (charSequence == null || charSequence.isEmpty()) {
                    filterResults.values = _myClientsListFiltered
                } else {
                    var searchString = charSequence.toString().toLowerCase()
                    for (item in _myClientsListFiltered) {
                        if (item.name.toLowerCase().contains(searchString) || item.address.toLowerCase().contains(searchString)) {
                            localList.add(item)
                        }
                    }
                    filterResults.values = localList
                }

                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filteredResults: FilterResults?
            ) {
                _myClientsList.clear()
                _myClientsList.addAll(filteredResults!!.values as ArrayList<Client>)
                notifyDataSetChanged()
            }

        }
    }

}