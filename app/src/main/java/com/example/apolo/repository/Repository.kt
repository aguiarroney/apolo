package com.example.apolo.repository

import com.example.apolo.models.Client
import com.google.android.gms.common.api.Api
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

private const val BASE_URL = "https://private-65965e-apolodata.apiary-mock.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MockApiService{
    @GET
    fun getClients(@Url url:String): Call<MutableList<Client>>
}

object RetrofiObject{
    val retrofitService: MockApiService by lazy{
        retrofit.create(MockApiService::class.java)
    }
}