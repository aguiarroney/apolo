package com.example.apolo.repository

import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.example.apolo.models.Polo
import retrofit2.Response

class Repository{

    suspend fun getClients(): Response<List<Client>> {
        return RetrofitInstance.mockApi.getClients()
    }

    suspend fun getPoloLimits(): Response<List<Polo>>{
        return RetrofitInstance.mockApi.getPoloLimits()
    }

    suspend fun getLeads(): Response<List<Lead>>{
        return RetrofitInstance.mockApi.getLeads()
    }
}