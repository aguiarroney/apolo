package com.example.apolo.api

import com.example.apolo.models.Client
import com.example.apolo.models.Lead
import com.example.apolo.models.Polo
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface MockApi{
    @GET("clients")
    suspend fun fetchClients(): Response<List<Client>>

    @GET("limits")
    suspend fun fetchPoloLimits(): Response<List<Polo>>

    @GET("leads")
    suspend fun fetchLeads(): Response<List<Lead>>

    @DELETE
    suspend fun deleteLead(@Url url: String) : Response<Lead>

    @DELETE
    suspend fun deleteClient(@Url url: String):Response<Client>

    @POST("clients")
    suspend fun postClient()

    @POST("leads")
    suspend fun posTLead()

}