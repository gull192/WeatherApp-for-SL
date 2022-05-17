package com.example.weather.data.remote.quest

data class WeatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Data>,
    val message: Int
)