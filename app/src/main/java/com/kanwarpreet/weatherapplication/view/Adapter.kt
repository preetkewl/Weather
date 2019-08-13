package com.kanwarpreet.weatherapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kanwarpreet.weatherapplication.R
import com.kanwarpreet.weatherapplication.model.Forecastday
import com.kanwarpreet.weatherapplication.model.Weather


import java.util.ArrayList

class Adapter(list: ArrayList<Forecastday>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    var dataList: ArrayList<Forecastday> = list

    fun  update(data: List<Forecastday>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_weather, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val obj: Forecastday = dataList[i]
        myViewHolder.tvDay.text = obj.date
        myViewHolder.tvTemp.text = obj.day.avgtempC.toString()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDay: TextView
        var tvTemp: TextView

        init {
            tvDay = itemView.findViewById(R.id.tvDay)
            tvTemp = itemView.findViewById(R.id.tvTempValue)
        }
    }
}
