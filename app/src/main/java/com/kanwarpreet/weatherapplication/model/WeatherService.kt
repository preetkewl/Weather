package com.kanwarpreet.weatherapplication.model

import com.kanwarpreet.weatherapplication.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class WeatherService {

    @Inject
    lateinit var api: WeatherApi

    init {
            DaggerApiComponent.create().inject(this)
    }

    fun getWeather(state: String): Single<Weather>{
        return api.getWeather("7f6799e5002b4bd6af394329190907", state, 4)
    }
}