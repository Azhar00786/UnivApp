package com.example.mviapp.ui.main.intent

sealed class MainIntent {
    data class FetchUniversityData(val countryName: String) : MainIntent()
}