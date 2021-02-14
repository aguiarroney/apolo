package com.example.apolo.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolo.models.Client
import com.example.apolo.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ClientViewModel(private val repository: Repository): ViewModel() {

    var clientsList: MutableLiveData<Response<List<Client>>> = MutableLiveData()

    fun fetchClients() {
        viewModelScope.launch {
            val response = repository.fetchClients()
            if (response != null) {
                if (response.isSuccessful)
                    clientsList.value = response
                else {
                    Log.i("ERRO CLIENTS API", "${response.code()}")
                }
            }
        }
    }
}