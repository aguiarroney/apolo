package com.example.apolo.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.MapsFragmentViewModel

class MapsFragmentViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapsFragmentViewModel(repository) as T
    }
}