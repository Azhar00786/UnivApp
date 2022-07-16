package com.example.mviapp.data.api

import com.example.mviapp.data.model.UniversityDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getUniversityData(@Query("country") countryName: String): List<UniversityDataModel>
}
