package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.data.BaseValues
import com.example.weatherapp.domain.UserPreferences
import com.example.weatherapp.domain.WeatherAPI
import com.example.weatherapp.domain.WeatherRepo
import com.example.weatherapp.presentation.repo.UserPreferencesImpl
import com.example.weatherapp.presentation.repo.WeatherRepoImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesWeatherApi(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(BaseValues.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesWeatherRepo(api: WeatherAPI, userPreferences: UserPreferences): WeatherRepo =
        WeatherRepoImpl(api, userPreferences)

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext app: Context): UserPreferences =
        UserPreferencesImpl(app)
}