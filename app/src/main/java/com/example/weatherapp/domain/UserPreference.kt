package com.example.weatherapp.domain

interface UserPreferences {
    suspend fun saveItem(key:String, value:String)
    suspend fun getItem(key:String):String?
}