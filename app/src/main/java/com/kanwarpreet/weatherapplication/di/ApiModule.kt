package com.kanwarpreet.weatherapplication.di

import com.kanwarpreet.weatherapplication.model.WeatherApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://api.apixu.com"

    @Provides
    fun provideWeatherApi(): WeatherApi{
        return  Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                build().
                create(WeatherApi::class.java)

    }
}