package com.example.weatherapp.domain

import com.example.weatherapp.data.ApiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") key:String = ApiKey.apiKey,
        @Query("q") city:String,
        @Query("aqi") aqi:String = "no"
    ): Response<WeatherDTO>
}