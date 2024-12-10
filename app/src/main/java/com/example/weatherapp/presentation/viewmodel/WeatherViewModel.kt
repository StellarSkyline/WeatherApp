package com.example.weatherapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.Current
import com.example.weatherapp.domain.Location
import com.example.weatherapp.domain.SearchDTOItem
import com.example.weatherapp.domain.WeatherDTO
import com.example.weatherapp.domain.WeatherRepo
import com.example.weatherapp.presentation.states.StateValues
import com.example.weatherapp.utils.ConnectionState
import com.example.weatherapp.utils.currentConnectivityState
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

    //TODO: Add Search API Functionality

    //UI States
    val currentWeatherState = savedStateHandle.getStateFlow("currentWeatherState", Current())
    val currentLocationState = savedStateHandle.getStateFlow("currentLocationState", Location())
    val uiState = savedStateHandle.getStateFlow("uiState", StateValues.Loading)
    val cityState = savedStateHandle.getStateFlow("cityState", "")
    val searchedCities = savedStateHandle.getStateFlow("searchedCities", mutableListOf<SearchDTOItem>())
    val currentList = savedStateHandle.getStateFlow("currentList", mutableListOf<WeatherDTO>())
    val savedData = savedStateHandle.getStateFlow("savedData", mutableListOf<String>())

    //DataStore Key
    private val key: String = "saved_weather"

    //User State handling
    fun changeCurrentCityState(city: String) {
        savedStateHandle["cityState"] = city
    }

    //Init - Handle No Network Connection and Check DataStore for location
    init {
        Log.d("STLog", "${app.currentConnectivityState}")
        when (app.currentConnectivityState) {
            ConnectionState.Available -> {
                checkDataStore()
            }

            ConnectionState.Unavailable -> {
                savedStateHandle["uiState"] = StateValues.NoConnectivity
            }
        }
    }

    //Function Calls
    fun getCurrentWeather(city: String) {
        savedStateHandle["uiState"] = StateValues.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrentWeather(city).let { response ->
                if (response.current != null) {
                    savedStateHandle["currentWeatherState"] = response.current
                    savedStateHandle["currentLocationState"] = response.location
                    repo.storeItem(key, city) // Saved to DataStore
                    savedStateHandle["uiState"] = StateValues.Success
                } else {
                    //Show "No City Found Error"
                    savedStateHandle["uiState"] = StateValues.Error
                }
            }
        }
    }

    fun getSearchCity(city: String) {
        savedStateHandle["uiState"] = StateValues.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repo.searchCity(city).let { response ->
                val list = mutableListOf<SearchDTOItem>()
                if (response.isNotEmpty()) {
                    response.forEach {
                        list.add(it)
                    }
                    savedStateHandle["searchedCities"] = list
                    getSearchCityCurrent()
                    savedStateHandle["uiState"] = StateValues.Success

                } else {
                    savedStateHandle["uiState"] = StateValues.Error
                }
            }
        }
    }

    private fun getSearchCityCurrent() {
        savedStateHandle["uiState"] = StateValues.Loading
        val list = mutableListOf<WeatherDTO>()
        viewModelScope.launch(Dispatchers.IO) {
            searchedCities.value.forEach {
                list.add(repo.getCurrentWeather("${it.lat},${it.lon}"))
            }
            savedStateHandle["currentList"] = list
            savedStateHandle["uiState"] = StateValues.Success
        }



    }

    //Check Data Store upon initialization of ViewModel,
    // if there's an item immediately call getCurrentWeather() api call
    private fun checkDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            val savedData = repo.getItem(key)
            Log.d("STLog", "Saved Data: $savedData")
            if (savedData == null) {
                savedStateHandle["uiState"] = StateValues.Empty
            } else {
                getCurrentWeather(savedData)
                savedStateHandle["uiState"] = StateValues.Success
            }
        }
    }
}