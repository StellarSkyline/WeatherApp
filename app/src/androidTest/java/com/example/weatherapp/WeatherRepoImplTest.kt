package com.example.weatherapp

import com.example.weatherapp.domain.SearchDTO
import com.example.weatherapp.domain.SearchDTOItem
import com.example.weatherapp.di.interfaces.UserPreferences
import com.example.weatherapp.di.interfaces.WeatherAPI
import com.example.weatherapp.domain.repo.WeatherRepoImpl
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class WeatherRepoImplTest {
    //mocks
    private val api: WeatherAPI = mock()
    private val userPreference: UserPreferences = mock()
    val repo = WeatherRepoImpl(api, userPreference)

    @Test
    fun `success get search city`() = runTest {
        `when`(api.searchCity(city = "London"))
            .thenReturn(Response.success(200, FakeSearchDTO()))
        val response = repo.searchCity("London")
        assert(response[0].name == "London")

    }

    @Test fun `get search city failure`() = runTest {
        `when`(api.searchCity(city = "London"))
            .thenReturn(Response.error(404, ResponseBody.create(null, "")))
        val response = repo.searchCity("London")
        assert(response.isEmpty())
    }

}

fun FakeSearchDTO(): SearchDTO {
    val data: ArrayList<SearchDTOItem> = arrayListOf(
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
    return SearchDTO().apply {
        addAll(data)
    }
}