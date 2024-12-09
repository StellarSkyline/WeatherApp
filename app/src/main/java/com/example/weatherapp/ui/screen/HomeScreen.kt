package com.example.weatherapp.ui.screen

import androidx.compose.runtime.Composable
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel

@Composable
fun HomeScreen(
    vm: WeatherViewModel,
    onNavigate: (String) -> Unit = {}
) {

    vm.getCurrentWeather("Chicago")


}