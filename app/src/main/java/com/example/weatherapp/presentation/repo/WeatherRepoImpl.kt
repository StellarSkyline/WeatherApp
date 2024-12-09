package com.example.weatherapp.presentation.repo

import com.example.weatherapp.domain.WeatherAPI
import com.example.weatherapp.domain.WeatherDTO
import com.example.weatherapp.domain.WeatherRepo

class WeatherRepoImpl(
    private val api: WeatherAPI
): WeatherRepo {
    override suspend fun getCurrentWeather(city: String): WeatherDTO {
        val response = api.getCurrentWeather(city = city)

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }
}