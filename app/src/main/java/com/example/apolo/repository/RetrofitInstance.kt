package com.example.apolo.repository

import com.example.apolo.api.MockApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://601ad5b10ee87c001706aa45.mockapi.io/api/v1/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
    }

    val mockApi: MockApi by lazy {
        retrofit.create(MockApi::class.java)
    }
}