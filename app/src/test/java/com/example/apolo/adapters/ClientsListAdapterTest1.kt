package com.example.apolo.adapters

import org.junit.Assert.*
import org.junit.Test
import com.example.apolo.adapters.ClientsListAdapter
import java.time.LocalDate

class ClientsListAdapterTest{


    //testando diferença de dias desde a ultima visita quando a data é no mesmo mes

    @Test
    fun `get correct number of days from now at the same month`(){

        val adapter = ClientsListAdapter()
        val dateString = "01/02/2021"

        val result = adapter.setLastVisit(dateString)

        assertEquals(5, result)
    }

    //testando diferença de dias desde a ultima visita quando a data é em mes anterior

    @Test
    fun `get correct number of days from now at diferent months`(){

        val adapter = ClientsListAdapter()
        val dateString = "31/01/2021"

        val result = adapter.setLastVisit(dateString)

        assertEquals(6, result)
    }

}