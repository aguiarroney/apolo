package com.example.apolo.repository

import com.example.apolo.models.Client
import com.google.android.gms.common.api.Api
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

class Repository{

    suspend fun getClients(): Response<List<Client>> {
        return RetrofitInstance.mockApi.getClients()
    }
}