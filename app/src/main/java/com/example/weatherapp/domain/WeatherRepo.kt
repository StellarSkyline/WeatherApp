package com.example.weatherapp.domain

interface WeatherRepo {
    suspend fun getCurrentWeather(city: String): WeatherDTO
    suspend fun searchCity(city: String): SearchDTO
    suspend fun getItem(key:String):String?
    suspend fun storeItem(key:String, value:String)
}