package com.example.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weatherapp.data.ProjectColors
import com.example.weatherapp.domain.Current
import com.example.weatherapp.domain.Location
import com.example.weatherapp.domain.SearchDTOItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    item: Location = Location(),
    current: Current = Current(),
    onClick: (String) -> Unit = {}
) {

    Card(
        modifier = modifier
            .width(336.dp)
            .height(117.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .background(ProjectColors.secondary_bg)
                .fillMaxSize()
                .padding(16.dp)
                .clickable {
                    onClick("${item.lat},${item.lon}")
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "${item.country}, ${item.region}", fontSize = 12.sp, color = ProjectColors.black)
                Text(text = item.name!!, fontSize = 12.sp, color = ProjectColors.black)
                Text(text = "${current.temp_f.toInt()}°", fontSize = 20.sp, color = ProjectColors.black)
            }
            Spacer(modifier = Modifier.weight(1f))
            Card(
                modifier = modifier
                    .height(113.dp)
                    .width(123.dp)
            ) {
                GlideImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ProjectColors.secondary_bg),
                    contentScale = ContentScale.Fit,
                    model = "https://${current.condition.icon}",
                    contentDescription = "Weather Icon"
                )
            }
        }
    }


}