package com.example.weatherapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.Current
import com.example.weatherapp.domain.Location
import com.example.weatherapp.domain.WeatherRepo
import com.example.weatherapp.presentation.states.StateValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val app: Application,
    val repo: WeatherRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //UI States
    val currentWeatherState = savedStateHandle.getStateFlow("currentWeatherState", Current())
    val currentLocationState = savedStateHandle.getStateFlow("currentLocationState", Location())
    val uiState = savedStateHandle.getStateFlow("uiState", StateValues.Loading)
    val cityState = savedStateHandle.getStateFlow("cityState", "")
    val isEmptyState = savedStateHandle.getStateFlow("isEmtpyState", true)

    //User State handling
    fun changeCurrentCityState(city: String) { savedStateHandle["cityState"] = city }

    //Function Calls
    fun getCurrentWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrentWeather(city).let { response ->
                if (response.current != null) {
                    savedStateHandle["currentWeatherState"] = response.current
                    savedStateHandle["currentLocationState"] = response.location
                    savedStateHandle["uiState"] = StateValues.Success
                }

            }
        }
    }

}