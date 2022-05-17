package com.example.weather

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers


import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    val disposable = CompositeDisposable()

    var lat:Double? = null
    var lon:Double? = null

    private lateinit var weatherMember: SharedPreferences

    lateinit var recyclerView: RecyclerView




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicialCoord()

        val button = findViewById<Button>(R.id.button)
        val search = findViewById<EditText>(R.id.search)
        val weatherNow = findViewById<TextView>(R.id.city)


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)



        lat = weatherMember.getFloat("lat",54.0f).toDouble()
        lon = weatherMember.getFloat("lon", 73.2f).toDouble()

        getWeatherFromCoord(lat, lon)

        button.setOnClickListener {

            val cityName = search.text.toString()
            getCoordFromCityName(cityName)


        }

    }

     fun inicialCoord(){
         weatherMember = getSharedPreferences("COORD", MODE_PRIVATE)
     }

    fun getWeatherFromCoord(lat: Double?, lon:Double?){
        disposable.add((application as App).questApi.getRequestWeather(lat,lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("TAG", "i m here")

                    val city = findViewById<TextView>(R.id.city)

                   var listWeather = it.list.map {
                        WeatherHolder(
                            dt_text = it.dt_txt,
                            temp_max = it.main.temp_max,
                            temp_min = it.main.temp_min,
                            description = it.weather[0].description
                        )
                    }
                    recyclerView.adapter = CustomRecyclerAdapter(listWeather)

                   city.text = weatherMember.getString("cityName", "Omsk")

                },
                {
                    Log.e("TAG", " lol ${it.message.toString()}")
                })
        )
    }

    fun getCoordFromCityName(cityName:String){

        val city = findViewById<TextView>(R.id.city)

        disposable.add((application as App).questApi.geocoding(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val editor = weatherMember.edit()

                    Log.d("TAG", "lat is ${it[0].lat}")

                    lat = it[0].lat
                    lon = it[0].lon
                    lat?.let { it1 -> editor.putFloat("lat", it1.toFloat()) }
                    lon?.let { it1 -> editor.putFloat("lon", it1.toFloat()) }
                    editor.putString("cityName",cityName)
                    editor.apply()

                    city.text=cityName
                    getWeatherFromCoord(lat, lon)

                },
                {
                    city.text = "Вы ввели некорретные данные"
                    Log.e("TAG", it.message.toString())
                }
            )
        )
    }
}
