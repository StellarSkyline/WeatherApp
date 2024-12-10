package com.example.weatherapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
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
import com.example.weatherapp.ui.components.EmptyLayout
import com.example.weatherapp.ui.components.IncludeSpinner
import com.example.weatherapp.ui.components.InputText
import com.example.weatherapp.ui.components.SearchItem

@Composable
fun SearchScreen(vm: WeatherViewModel, onNavigate: (String) -> Unit = {}) {
    //States
    val searchedCities by vm.searchedCities.collectAsStateWithLifecycle()
    val cityState by vm.cityState.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    //Constraint Sets
    val constraints = ConstraintSet {
        //constraint ids
        val searchBar = createRefFor("searchBar")
        val includeSpinner = createRefFor("includeSpinner")
        val emptyLocation = createRefFor("emptyLocation")
        val rv_cities = createRefFor("rv_cities")

        //constrains
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

        constrain(emptyLocation) {
            top.linkTo(searchBar.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(rv_cities) {
            top.linkTo(searchBar.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
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
            },
            onValueChanged = {
                vm.changeCurrentCityState(it)
            }
        )

        when (uiState) {
            StateValues.Loading -> {
                IncludeSpinner(modifier = Modifier.layoutId("includeSpinner"))
            }

            StateValues.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .layoutId("rv_cities"),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(searchedCities.size) {
                        SearchItem(
                            item = searchedCities[it]
                        ) {
                            vm.getCurrentWeather(it)
                            onNavigate(Screen.HomeScreen.route)
                        }
                    }
                }
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
