package com.kanwarpreet.weatherapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kanwarpreet.weatherapplication.model.Weather
import com.kanwarpreet.weatherapplication.model.WeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    private val service = WeatherService()
    private val disposable = CompositeDisposable()

    val weatherDetails = MutableLiveData<Weather>()
    val weatherLoadingError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(state: String) {
        loadWeather(state)
    }

    private fun loadWeather(state: String) {
        loading.value = true

        disposable.add(
                service.getWeather(state).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : DisposableSingleObserver<Weather>() {
                    override fun onSuccess(value: Weather?) {
                        loading.value = false
                        weatherDetails.value = value
                        weatherLoadingError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loading.value = false
                        weatherLoadingError.value = true
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}