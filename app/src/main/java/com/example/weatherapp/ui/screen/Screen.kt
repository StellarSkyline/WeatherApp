package com.example.weatherapp.ui.screen

sealed class Screen(val route: String)  {
    object HomeGraph : Screen("home_graph")
    object HomeScreen : Screen("home_screen")
    object SearchScreen: Screen("search_screen")
}