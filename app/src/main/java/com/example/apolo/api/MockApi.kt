package com.example.apolo.api

import com.example.apolo.models.Client
import com.example.apolo.models.Polo
import com.google.android.gms.maps.model.LatLng
import retrofit2.Response
import retrofit2.http.GET

interface MockApi{
    @GET("clients")
    suspend fun getClients(): Response<List<Client>>

    @GET("limits")
    suspend fun getPoloLimits(): Response<List<Polo>>
}