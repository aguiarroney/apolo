package com.example.apolo.adapters

import android.os.Build
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
import java.util.*
import kotlin.collections.ArrayList

class ClientsListAdapter : RecyclerView.Adapter<ClientsListAdapter.ViewHolder>(), Filterable {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.tv_name)
        var itemAddress: TextView = itemView.findViewById(R.id.tv_address)
        var itemNexVisit: TextView = itemView.findViewById(R.id.tv_next_visit_text)
        var itemLastVisit: TextView = itemView.findViewById(R.id.tv_last_visit_text)
        var itemtpv: TextView = itemView.findViewById(R.id.tv_tpv_text)
        var itemSatisfaction: TextView = itemView.findViewById(R.id.tv_client_satsfaction)
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
        holder.itemLastVisit.text = holder.itemView.context.getString(R.string.dias, setLastVisit(_myClientsList[position].lastVisit).toString())

        holder.itemtpv.text = _myClientsList[position].tpv.toString()
        holder.itemSatisfaction.text = _myClientsList[position].satisfaction
    }

    fun setData(newList: List<Client>) {
        _myClientsListFiltered.clear()
        _myClientsList.clear()
        _myClientsList.addAll(newList as ArrayList<Client>)
        _myClientsListFiltered.addAll(newList)
        notifyDataSetChanged()
    }

    private fun getDaysInMonth(month: Int): Int {
        return if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) 31
        else if (month == 4 || month == 6 || month == 9 || month == 11) 30
        else 28
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
            days = if (lastDay > dayNow)
                (getDaysInMonth(lastMonth) - lastDay) + dayNow
            else
                dayNow - lastDay
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
                    val searchString = charSequence.toString().toLowerCase(Locale.ROOT)
                    for (item in _myClientsListFiltered) {
                        if (item.name.toLowerCase(Locale.ROOT).contains(searchString) || item.address.toLowerCase(
                                Locale.ROOT
                            ).contains(searchString)) {
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