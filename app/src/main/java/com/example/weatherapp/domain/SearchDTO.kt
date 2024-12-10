package com.example.weatherapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class SearchDTO : ArrayList<SearchDTOItem>()

@Parcelize
data class SearchDTOItem(
    val country: String? = null,
    val id: Int? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val name: String? = null,
    val region: String? = null,
    val url: String? = null
): Parcelable