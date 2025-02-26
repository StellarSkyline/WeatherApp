package com.example.weatherapp.di.interfaces

import com.example.weatherapp.data.ApiKey
import com.example.weatherapp.domain.SearchDTO
import com.example.weatherapp.domain.WeatherDTO
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

    @GET("search.json")
    suspend fun searchCity(
        @Query("key") key:String = ApiKey.apiKey,
        @Query("q") city:String
    ): Response<SearchDTO>
}