package com.example.apolo.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolo.models.Lead
import com.example.apolo.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class LeadViewModel(private val repository: Repository): ViewModel() {

    var leadsList: MutableLiveData<Response<List<Lead>>> = MutableLiveData()

    fun fetchLeads() {
        viewModelScope.launch {
            val response = repository.fetchLeads()
            if (response != null) {
                if (response.isSuccessful)
                    leadsList.value = response
                else {
                    Log.i("ERRO LEADS API", "${response.code()}")
                }
            }
        }
    }
}