package com.example.guesstheflag

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.guesstheflag.databinding.ActivityMainBinding
import com.google.gson.Gson
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import kotlin.concurrent.fixedRateTimer
import java.io.InputStreamReader



class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private val updateInterval: Long = 750 // 1 saniye

    val mainHandler = Handler(Looper.getMainLooper())

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var countryData: CountryData



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val jsonString = loadJSONFromAssets("countries.json")

        countryData = Gson().fromJson(jsonString, CountryData::class.java)

        startFlagRotation()
    }





    private fun loadJSONFromAssets(fileName: String): String? {
        return try {
            val inputStream = assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            reader.readText()
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun start (view: View){

        val intent = Intent(this@MainActivity, GameActivity::class.java)
        startActivity(intent)

    }

    private fun startFlagRotation() {
        handler.post(object : Runnable {
            override fun run() {
                if (countryData.data.isNotEmpty()) {
                    val rnds = (0..249).random()
                    binding.startFlagImage.setImageResource(resources.getIdentifier(countryData.data[rnds].code,"drawable",packageName))

                }
                handler.postDelayed(this, updateInterval)
            }
        })
    }








}