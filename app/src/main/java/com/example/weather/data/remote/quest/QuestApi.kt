package com.example.weather.data.remote.quest

import io.reactivex.Observable
import retrofit2.http.*

interface QuestApi {

    @GET("./geo/1.0/direct?limit=1&appid=5d3853f437b2aefdf641d25e593c5ade")
    @Headers("Content-Type: application/json")
    fun geocoding(@Query("q") cityName: String) : Observable<List<Coord>>


    @GET("./data/2.5/forecast?&&appid=5d3853f437b2aefdf641d25e593c5ade&units=metric&lang=ru")
    @Headers("Content-Type: application/json")
    fun getRequestWeather(@Query("lat") lat:Double?, @Query("lon") lon:Double?) :Observable<WeatherData>


}