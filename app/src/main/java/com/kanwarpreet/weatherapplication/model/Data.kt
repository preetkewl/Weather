package com.kanwarpreet.weatherapplication.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Weather(
        @SerializedName("forecast")
        val forecast: Forecast,

        @SerializedName("current")
        val current: Current
)

data class Forecast(
        @SerializedName("forecastday")
        val forecastday: List<Forecastday>?
)

data class Current(
        @SerializedName("temp_c")
        val temp_c: String
)

data class Forecastday(
        @SerializedName("day")
        val day: Day,
        @SerializedName("date")
        val date:String
)

data class Day(
        @SerializedName("avgtemp_c")
        @Expose
        val avgtempC:Double
)

