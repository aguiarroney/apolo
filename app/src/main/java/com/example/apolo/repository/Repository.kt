package com.example.apolo.repository

import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.example.apolo.models.Polo
import retrofit2.Response

class Repository{

    suspend fun fetchClients(): Response<List<Client>> {
        return RetrofitInstance.mockApi.fetchClients()
    }

    suspend fun fetchPoloLimits(): Response<List<Polo>>{
        return RetrofitInstance.mockApi.fetchPoloLimits()
    }

    suspend fun fetchLeads(): Response<List<Lead>>{
        return RetrofitInstance.mockApi.fetchLeads()
    }

    suspend fun deleteLead(url: String) : Response<Lead>{
        return RetrofitInstance.mockApi.deleteLead(url)
    }

    suspend fun deleteClient(url: String): Response<Client> {
        return RetrofitInstance.mockApi.deleteClient(url)
    }
}