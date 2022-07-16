package com.example.mviapp.ui.main.viewstate

import com.example.mviapp.data.model.UniversityDataModel

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class UniData(val uniDataList: List<UniversityDataModel>) : MainState()
    data class Error(val error: String?) : MainState()
}