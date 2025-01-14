package com.example.weatherapp.di.interfaces

interface UserPreferences {
    suspend fun saveItem(key:String, value:String)
    suspend fun getItem(key:String):String?
}