package com.example.apolo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolo.models.Client
import com.example.apolo.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ListFragmentViewModel(private val repository: Repository): ViewModel() {

    var myClients: MutableLiveData<Response<List<Client>>> = MutableLiveData()

    fun getClients(){
        viewModelScope.launch {
            val response = repository.getClients()
            myClients.value = response
        }
    }
}