package com.example.mviapp.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviapp.data.repository.MainRepository
import com.example.mviapp.ui.main.intent.MainIntent
import com.example.mviapp.ui.main.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    companion object {
        const val TAG = "MainViewModel"
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchUniversityData -> fetchUniData(it.countryName)
                }
            }
        }
    }

    private fun fetchUniData(countryName: String) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.UniData(repository.getUniversityData(countryName))
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }
}