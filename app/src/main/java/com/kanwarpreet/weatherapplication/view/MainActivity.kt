package com.kanwarpreet.weatherapplication.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle

import com.kanwarpreet.weatherapplication.R
import com.kanwarpreet.weatherapplication.viewModel.ListViewModel
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import android.location.Geocoder
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ListViewModel
    private val adapterWeather = Adapter(arrayListOf())
    lateinit var cityName: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var animation: Animation
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        } else {
            updateView()
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateView(){
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location!!.latitude, location!!.longitude, 1)
            cityName = addresses[0].adminArea
            tvCity.text = cityName
            viewModel.refresh(cityName)
        }

        btnRetry.setOnClickListener { viewModel.refresh(cityName) }
        rvDays.apply {
            layoutManager = LinearLayoutManager(context)!!
            adapter = adapterWeather
        }
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {

        viewModel.weatherDetails.observe(this, Observer { list ->
            list?.let {
                tvTemperature.text = list.current.temp_c + "°"
                adapterWeather.update(list.forecast.forecastday!!)
            }
        })
        viewModel.weatherLoadingError.observe(this, Observer { bool ->

            if (bool!!) {
                llErrorScreen.visibility = View.VISIBLE
            }
        })
        viewModel.loading.observe(this, Observer { bool ->
            bool?.let {
                if (it) {
                    llLoading.visibility = View.VISIBLE
                    ivLoading.animation = animation
                    animation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {}

                        override fun onAnimationEnd(animation: Animation?) {
                            ivLoading.startAnimation(animation)
                        }

                        override fun onAnimationStart(animation: Animation?) {}
                    }
                    )
                } else {
                    llLoading.visibility = View.GONE
                }
            }
        })

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    updateView()
                } else {
                    finishAffinity()
                }
                return
            }
        }
    }
}