package com.example.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weatherapp.data.ProjectColors
import com.example.weatherapp.domain.Current
import com.example.weatherapp.domain.Location

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CurrentWeatherDetail(
    modifier: Modifier = Modifier,
    weather: Current = Current(),
    location: Location = Location()
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = modifier
                .height(113.dp)
                .width(123.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentScale = ContentScale.Fit,
                model = "https://${weather.condition.icon}",
                contentDescription = "Weather Icon"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = location.name,
                color = Color.Black,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
            )
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location Icon",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = " ${weather.temp_f.toInt()}°",
            color = Color.Black,
            fontSize = 70.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = modifier
                .height(75.dp)
                .width(274.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ProjectColors.secondary_bg)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Humidity", color = ProjectColors.secondary_txt, fontSize = 12.sp)
                    Text(
                        text = "${weather.humidity}%",
                        color = ProjectColors.primary_txt,
                        fontSize = 16.sp
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "UV", color = ProjectColors.secondary_txt, fontSize = 12.sp)
                    Text(
                        text = "${weather.uv}",
                        color = ProjectColors.primary_txt,
                        fontSize = 16.sp
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Feels Like", color = ProjectColors.secondary_txt, fontSize = 12.sp)
                    Text(
                        text = "${weather.feelslike_f}",
                        color = ProjectColors.primary_txt,
                        fontSize = 16.sp
                    )
                }
            }
        }


    }
}