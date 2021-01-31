package com.example.apolo.api

import com.example.apolo.models.Client
import retrofit2.Response
import retrofit2.http.GET

interface MockApi{
    @GET("clients")
    suspend fun getClients(): Response<List<Client>>
}