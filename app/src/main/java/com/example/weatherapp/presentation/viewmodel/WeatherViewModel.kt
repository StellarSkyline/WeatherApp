package com.example.weatherapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val app:Application,
    val repo: WeatherRepo
): ViewModel() {

    fun getCurrentWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("STLog","Weather DTO: ${repo.getCurrentWeather(city)}" )
        }
    }

}