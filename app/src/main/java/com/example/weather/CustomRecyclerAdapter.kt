package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(val weatherData : List<WeatherHolder>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>(){

    public val countDay = 5

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val date = itemView.findViewById<TextView>(R.id.date)
        val temp_min = itemView.findViewById<TextView>(R.id.temp_min)
        val temp_max = itemView.findViewById<TextView>(R.id.temp_max)
        val description = itemView.findViewById<TextView>(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_data, parent, false)
        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return weatherData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val celsia = "°C"
        holder.date.text = weatherData[position].dt_text
        holder.temp_min.text = weatherData[position].temp_min.toInt().toString() + celsia
        holder.temp_max.text = weatherData[position].temp_max.toInt().toString() + celsia
        holder.description.text = weatherData[position].description
    }

}