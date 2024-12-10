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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.ProjectColors
import com.example.weatherapp.domain.SearchDTOItem

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    item: SearchDTOItem = SearchDTOItem(),
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
                Text(text = item.country!!, fontSize = 12.sp, color = ProjectColors.black)
                Text(text = item.region!!, fontSize = 12.sp, color = ProjectColors.black)
                Text(text = item.name!!, fontSize = 20.sp, color = ProjectColors.black)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(70.dp),
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location Icon",
            )
        }
    }


}