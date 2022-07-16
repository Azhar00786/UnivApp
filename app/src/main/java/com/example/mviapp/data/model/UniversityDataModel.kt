package com.example.mviapp.data.model

import com.google.gson.annotations.SerializedName

data class UniversityDataModel(
    val alpha_two_code: String,
    val country: String,
    val domains: List<String>,
    val name: String,
    @SerializedName("state-province")
    val state_province: String,
    val web_pages: List<String>
)