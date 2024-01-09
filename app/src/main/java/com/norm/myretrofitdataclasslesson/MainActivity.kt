package com.norm.myretrofitdataclasslesson

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.norm.myretrofitdataclasslesson.databinding.ActivityMainBinding
import com.norm.myretrofitdataclasslesson.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        binding.request.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val api = retrofit.create(MainApi::class.java)
                val model = api.getWeatherData(
                    key = "9ffe10fdc3d2447681a100356231812",
                    q = "Novosibirsk",
                    days = "1",
                    aqi = "no",
                    alerts = "no"
                )
                runOnUiThread {
                    binding.tvTemperature.text = model.current.temp_c.toString()
                    binding.tvDate.text = model.location.localtime
                }
            }
        }
    }
}