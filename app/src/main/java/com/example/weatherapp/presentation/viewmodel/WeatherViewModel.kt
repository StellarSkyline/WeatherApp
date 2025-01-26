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
import com.example.weatherapp.di.interfaces.WeatherRepo
import com.example.weatherapp.presentation.states.StateValues
import com.example.weatherapp.utils.ConnectionState
import com.example.weatherapp.utils.currentConnectivityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
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
    val searchedCities =
        savedStateHandle.getStateFlow("searchedCities", mutableListOf<SearchDTOItem>())
    val currentList = savedStateHandle.getStateFlow("currentList", mutableListOf<WeatherDTO>())

    //channel
    private val channel = Channel<Job>(2).apply {
        viewModelScope.launch(Dispatchers.IO) {
            consumeEach { it.join() }
        }
    }

    //DataStore Key
    private val key: String = "saved_weather"

    //User State handling
    fun changeCurrentCityState(city: String) {
        savedStateHandle["cityState"] = city
    }

    fun checkConnectivity() {
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

    fun  getSearchCity(city: String) {
        //Set uiState to Loading
        savedStateHandle["uiState"] = StateValues.Loading

        //Create first network call, search for cities and save to savedStateHandle
        val searchJob =
            viewModelScope.launch(context = Dispatchers.IO, start = CoroutineStart.LAZY) {
                repo.searchCity(city).let { response ->
                    val list = mutableListOf<SearchDTOItem>()
                    repo.searchCity(city).let { response ->
                        if (response.isNotEmpty()) response.forEach { list.add(it) }
                        else savedStateHandle["uiState"] = StateValues.Error
                    }
                    savedStateHandle["searchedCities"] = list
                }
            }

        //use saved searched cities in the getCurrentWeather using lat long to be more precise
        val getCurrentCityJob =
            viewModelScope.launch(context = Dispatchers.IO, start = CoroutineStart.LAZY) {
                val list = mutableListOf<WeatherDTO>()
                searchedCities.value.forEach {
                    list.add(repo.getCurrentWeather("${it.lat},${it.lon}"))
                }
                if (list.isEmpty()) {
                    savedStateHandle["uiState"] = StateValues.Error
                } else {
                    savedStateHandle["currentList"] = list
                    savedStateHandle["uiState"] = StateValues.Success
                }

            }
        //add jobs to channel
        channel.trySend(searchJob)
        channel.trySend(getCurrentCityJob)
    }

    //Check Data Store upon initialization of ViewModel,
    // if there's an item immediately call getCurrentWeather() api call
     fun checkDataStore() {
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