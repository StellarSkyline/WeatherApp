package com.example.weatherapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.weatherapp.extension.sharedViewModel
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import com.example.weatherapp.ui.screen.HomeScreen
import com.example.weatherapp.ui.screen.Screen

@Composable
fun AppNavigation(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeGraph.route) {

        //Login Graph
        navigation(
            startDestination = Screen.HomeScreen.route,
            route = Screen.HomeGraph.route
        ) {
            composable(route = Screen.HomeScreen.route) { entry ->
                val viewModel = entry.sharedViewModel<WeatherViewModel>(navController)
                HomeScreen(vm = viewModel) {
                    navController.navigate(it)

                }
            }
        }


    }
}