package com.example.weatherapp

import com.example.weatherapp.domain.SearchDTO
import com.example.weatherapp.domain.SearchDTOItem

class FakeResponses() {

    fun FakeSearchDTO(): SearchDTO {
//    val data: ArrayList<SearchDTOItem> = arrayListOf(
//        SearchDTOItem(
//            country = "UK",
//            id = 0,
//            lat = 0.0,
//            lon = 0.0,
//            name = "London",
//            region = "London",
//            url = "London"
//        )
//    )
        return SearchDTO().apply {
            //addAll(data)
            add(
                SearchDTOItem(
                country = "UK",
                id = 0,
                lat = 0.0,
                lon = 0.0,
                name = "London",
                region = "London",
                url = "London"
            )
            )
        }
    }
}