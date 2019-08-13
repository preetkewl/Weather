package com.kanwarpreet.weatherapplication.di

import com.kanwarpreet.weatherapplication.model.WeatherService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent{

    fun inject(service: WeatherService)

}
