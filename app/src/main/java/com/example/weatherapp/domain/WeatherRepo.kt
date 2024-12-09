package com.example.weatherapp.domain

interface WeatherRepo {
    suspend fun getCurrentWeather(city: String): WeatherDTO

}