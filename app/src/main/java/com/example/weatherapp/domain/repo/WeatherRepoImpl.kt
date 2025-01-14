package com.example.weatherapp.domain.repo

import android.util.Log
import com.example.weatherapp.domain.SearchDTO
import com.example.weatherapp.di.interfaces.UserPreferences
import com.example.weatherapp.di.interfaces.WeatherAPI
import com.example.weatherapp.domain.WeatherDTO
import com.example.weatherapp.di.interfaces.WeatherRepo

class WeatherRepoImpl(
    private val api: WeatherAPI,
    private val userPreferences: UserPreferences
) : WeatherRepo {
    override suspend fun getCurrentWeather(city: String): WeatherDTO {
        val response = api.getCurrentWeather(city = city)

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            //return null weather DTO
            Log.d("STLog", "Error: ${response.errorBody()}")
            return WeatherDTO(null, null)
        }
    }

    override suspend fun searchCity(city: String): SearchDTO {
        val response = api.searchCity(city = city)
        if(response.isSuccessful) {
            return response.body()!!
        } else {
            //return null search DTO
            Log.d("STLog", "Error: ${response.errorBody()}")
            return SearchDTO()
        }
    }

    override suspend fun getItem(key: String): String? = userPreferences.getItem(key)

    override suspend fun storeItem(key: String, value: String) {
        userPreferences.saveItem(key, value)
    }

}