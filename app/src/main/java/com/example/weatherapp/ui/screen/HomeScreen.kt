package com.example.weatherapp.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.presentation.states.StateValues
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import com.example.weatherapp.ui.components.CurrentWeatherDetail
import com.example.weatherapp.ui.components.EmptyLayout
import com.example.weatherapp.ui.components.IncludeSpinner
import com.example.weatherapp.ui.components.InputText

@Composable
fun HomeScreen(
    vm: WeatherViewModel,
    onNavigate: (String) -> Unit = {}
) {
    //States
    val currentWeather by vm.currentWeatherState.collectAsStateWithLifecycle()
    val currentLocation by vm.currentLocationState.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val cityState by vm.cityState.collectAsStateWithLifecycle()

    //constrain set
    val constraints = ConstraintSet {
        //constrain labels
        val searchBar = createRefFor("searchBar")
        val currentWeather = createRefFor("currentWeather")
        val includeSpinner = createRefFor("includeSpinner")
        val emptyeLocation = createRefFor("emptyLocation")

        //constraints
        constrain(searchBar) {
            top.linkTo(parent.top, 44.dp)
            start.linkTo(parent.start, 24.dp)
            end.linkTo(parent.end, 24.dp)
            width = Dimension.value(327.dp)
            height = Dimension.wrapContent
        }

        constrain(includeSpinner) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints

        }

        constrain(emptyeLocation) {
            top.linkTo(searchBar.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(currentWeather) {
            top.linkTo(searchBar.bottom, 32.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints

        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        constraintSet = constraints
    ) {
        InputText(
            modifier = Modifier.layoutId("searchBar"),
            value = cityState,
            enabled = true,
            onAction = {

                vm.getSearchCity(cityState)
                onNavigate(Screen.SearchScreen.route)
            },
            onValueChanged = {
                vm.changeCurrentCityState(it)
            }
        )

        //State Module Logic
        when (uiState) {
            StateValues.Loading -> {
                IncludeSpinner(modifier = Modifier.layoutId("includeSpinner"))
            }
            StateValues.Success -> {
                CurrentWeatherDetail(
                    modifier = Modifier.layoutId("currentWeather"),
                    location = currentLocation,
                    weather = currentWeather
                )
            }
            StateValues.Empty -> {
                EmptyLayout(
                    modifier = Modifier.layoutId("emptyLocation")
                )
            }
            StateValues.Error -> {
                EmptyLayout(
                    modifier = Modifier.layoutId("emptyLocation"),
                    text = "No City Found"
                )
            }
            StateValues.NoConnectivity -> {
                EmptyLayout(
                    modifier = Modifier.layoutId("emptyLocation"),
                    text = "No Access to Internet"
                )
            }
        }
    }
}