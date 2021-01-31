package com.example.apolo.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apolo.repository.Repository
import com.example.apolo.viewmodels.ListFragmentViewModel

class ListFragmentViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListFragmentViewModel(repository) as T
    }
}