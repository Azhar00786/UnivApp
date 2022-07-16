package com.example.mviapp.data.repository

import com.example.mviapp.data.api.ApiConnectorSingelton
import com.example.mviapp.data.api.ApiService
import com.example.mviapp.data.model.UniversityDataModel

class MainRepository(private val apiConnectorSingelton: ApiConnectorSingelton) {
    private fun getApiConnector(): ApiService {
        val retrofitInstance = apiConnectorSingelton.getRetrofitInstance()
        return retrofitInstance.create(ApiService::class.java)
    }

    suspend fun getUniversityData(countryName: String): List<UniversityDataModel> {
        return getApiConnector().getUniversityData(countryName)
    }
}