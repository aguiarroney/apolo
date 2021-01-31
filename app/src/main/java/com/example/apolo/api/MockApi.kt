package com.example.apolo.api

import android.util.Log
import com.example.apolo.models.Client
import com.example.apolo.repository.RetrofiObject
import com.google.android.gms.common.api.Api
import retrofit2.Call
import retrofit2.Response

class MockApi {

    fun getClients(){
        RetrofiObject.retrofitService.getClients("clients").enqueue(object: retrofit2.Callback<MutableList<Client>>{
            override fun onResponse(
                call: Call<MutableList<Client>>,
                response: Response<MutableList<Client>>
            ) {
                var res = response.body()
                Log.i("RESPONSE", "$res")
            }

            override fun onFailure(call: Call<MutableList<Client>>, t: Throwable) {
                Log.i("RESPONSE ERRO", "${t.message}")
            }

        })
    }

}