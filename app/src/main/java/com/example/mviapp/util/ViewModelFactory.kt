package com.example.mviapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mviapp.data.api.ApiConnectorSingelton
import com.example.mviapp.data.repository.MainRepository
import com.example.mviapp.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModelFactory(private val apiConnectorSingelton: ApiConnectorSingelton) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiConnectorSingelton)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}