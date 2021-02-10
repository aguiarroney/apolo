package com.example.apolo.repository

import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.example.apolo.models.Polo
import retrofit2.Response

class Repository {

    suspend fun fetchClients(): Response<List<Client>>? {
        return try {

            RetrofitInstance.mockApi.fetchClients()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchPoloLimits(): Response<List<Polo>>? {
        return try {
            RetrofitInstance.mockApi.fetchPoloLimits()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchLeads(): Response<List<Lead>>? {
        return try {
            RetrofitInstance.mockApi.fetchLeads()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteLead(url: String): Response<Lead>? {
        return try {
            RetrofitInstance.mockApi.deleteLead(url)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteClient(url: String): Response<Client>? {
        return try {
            RetrofitInstance.mockApi.deleteClient(url)
        } catch (e: Exception) {
            null
        }
    }

}