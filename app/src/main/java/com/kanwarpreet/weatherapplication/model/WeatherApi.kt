package com.kanwarpreet.weatherapplication.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/forecast.json")
    fun getWeather(@Query("key") key: String,@Query("q") state: String,@Query("days") days: Int): Single<Weather>

}
