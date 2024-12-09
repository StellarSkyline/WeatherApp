package com.example.weatherapp.presentation.repo

import android.util.Log
import com.example.weatherapp.domain.UserPreferences
import com.example.weatherapp.domain.WeatherAPI
import com.example.weatherapp.domain.WeatherDTO
import com.example.weatherapp.domain.WeatherRepo

class WeatherRepoImpl(
    private val api: WeatherAPI,
    private val userPreferences: UserPreferences
) : WeatherRepo {
    override suspend fun getCurrentWeather(city: String): WeatherDTO {
        val response = api.getCurrentWeather(city = city)

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            //return empty weather DTO
            Log.d("STLog", "Error: ${response.errorBody()}")
            return WeatherDTO()
        }
    }
}