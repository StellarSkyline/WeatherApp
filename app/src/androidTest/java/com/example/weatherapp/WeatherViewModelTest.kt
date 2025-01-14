package com.example.weatherapp

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.example.weatherapp.di.interfaces.WeatherRepo
import com.example.weatherapp.presentation.states.StateValues
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class WeatherViewModelTest {
    @get:Rule
    var rule = HiltAndroidRule(this)

    @Inject lateinit var weatherRepo: WeatherRepo
    @ApplicationContext val app = Application()
    private val savedStateHandle = SavedStateHandle()
    private lateinit var weatherViewModel: WeatherViewModel


    @Before
    fun init() {
        rule.inject()
        weatherViewModel = WeatherViewModel(app, weatherRepo, savedStateHandle)
    }
    
    @Test
    fun `Default State Test`() {
        assert(weatherViewModel.uiState.value == StateValues.Loading)
        assert(weatherViewModel.currentWeatherState.value.cloud == 0)
        assert(weatherViewModel.currentLocationState.value.name == "")
        assert(weatherViewModel.cityState.value == "")
        assert(weatherViewModel.searchedCities.value.isEmpty())
        assert(weatherViewModel.currentList.value.isEmpty())
    }
}


