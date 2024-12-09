package com.example.weatherapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class WeatherDTO(
    val current: Current = Current(),
    val location: Location = Location()
)
@Parcelize
data class Location(
    val country: String = "",
    val lat: Double = 0.0,
    val localtime: String = "",
    val localtime_epoch: Int = 0,
    val lon: Double = 0.0,
    val name: String = "",
    val region: String = "",
    val tz_id: String = ""
): Parcelable

@Parcelize
data class Current(
    val cloud: Int = 0,
    val condition: Condition = Condition(),
    val dewpoint_c: Double = 0.0,
    val dewpoint_f: Double = 0.0,
    val feelslike_c: Double = 0.0,
    val feelslike_f: Double = 0.0,
    val gust_kph: Double = 0.0,
    val gust_mph: Double = 0.0,
    val heatindex_c: Double = 0.0,
    val heatindex_f: Double = 0.0,
    val humidity: Int = 0,
    val is_day: Int = 0,
    val last_updated: String = "",
    val last_updated_epoch: Int = 0,
    val precip_in: Double = 0.0,
    val precip_mm: Double = 0.0,
    val pressure_in: Double = 0.0,
    val pressure_mb: Double = 0.0,
    val temp_c: Double = 0.0,
    val temp_f: Double = 0.0,
    val uv: Double = 0.0,
    val vis_km: Double = 0.0,
    val vis_miles: Double = 0.0,
    val wind_degree: Int = 0,
    val wind_dir: String = "",
    val wind_kph: Double = 0.0,
    val wind_mph: Double = 0.0,
    val windchill_c: Double = 0.0,
    val windchill_f: Double = 0.0
): Parcelable

@Parcelize
data class Condition(
    val code: Int = 0,
    val icon: String = "",
    val text: String = ""
): Parcelable