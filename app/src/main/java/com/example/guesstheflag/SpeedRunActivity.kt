package com.example.guesstheflag


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.guesstheflag.databinding.ActivityGameBinding
import com.example.guesstheflag.databinding.ActivitySpeedRunBinding
import com.google.gson.Gson
import java.io.InputStreamReader


class SpeedRunActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySpeedRunBinding
    private lateinit var countryData: CountryData
    private lateinit var currentFlag: String
    private var randomOption: Int? = null
    private var wrongAnswerCount = 3
    var score: Int = 0
    val pastQuestions = ArrayList<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySpeedRunBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val jsonString = loadJSONFromAssets("countries.json")

        countryData = Gson().fromJson(jsonString, CountryData::class.java)

        newQuestion()


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

    fun randomInts (): Set<Int>{
        val randoms = generateSequence {
            (0..249).random()
        }.distinct().take(4).toSet()

        return randoms
    }

    fun newQuestion(){

        var randomCountry = randomInts()

        while (pastQuestions.contains(randomCountry.elementAt(0))){
            randomCountry = randomInts()
        }
        pastQuestions.add(randomCountry.elementAt(0))



        currentFlag = randomCountry.elementAt(0).toString()
        binding.questionFlag.setImageResource(resources.getIdentifier(countryData.data[randomCountry.elementAt(0)].code, "drawable", packageName))
        randomOption = (1..4).random()

        when (randomOption){
            1 -> {
                binding.option1.text = countryData.data[randomCountry.elementAt(0)].name
                binding.option2.text = countryData.data[randomCountry.elementAt(1)].name
                binding.option3.text = countryData.data[randomCountry.elementAt(2)].name
                binding.option4.text = countryData.data[randomCountry.elementAt(3)].name
            }

            2 -> {
                binding.option1.text = countryData.data[randomCountry.elementAt(1)].name
                binding.option2.text = countryData.data[randomCountry.elementAt(0)].name
                binding.option3.text = countryData.data[randomCountry.elementAt(2)].name
                binding.option4.text = countryData.data[randomCountry.elementAt(3)].name
            }

            3 -> {
                binding.option1.text = countryData.data[randomCountry.elementAt(2)].name
                binding.option2.text = countryData.data[randomCountry.elementAt(1)].name
                binding.option3.text = countryData.data[randomCountry.elementAt(0)].name
                binding.option4.text = countryData.data[randomCountry.elementAt(3)].name
            }

            4 -> {
                binding.option1.text = countryData.data[randomCountry.elementAt(3)].name
                binding.option2.text = countryData.data[randomCountry.elementAt(1)].name
                binding.option3.text = countryData.data[randomCountry.elementAt(2)].name
                binding.option4.text = countryData.data[randomCountry.elementAt(0)].name
            }
        }







    }



    fun option1(view: View){

        if (pastQuestions.size == 250){
            val intent = Intent(this@SpeedRunActivity, FinishActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }

        else if (countryData.data[currentFlag.toInt()].name == binding.option1.text) {

            newQuestion()
            increaseScore()
        }
        else{
            wrongAnswer()
            wrongAnswerCounter()
        }


    }

    fun option2(view: View){

        if (pastQuestions.size == 250){
            val intent = Intent(this@SpeedRunActivity, FinishActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }

        else if (countryData.data[currentFlag.toInt()].name == binding.option2.text) {
            newQuestion()
            increaseScore()
        }
        else{
            wrongAnswer()
            wrongAnswerCounter()
        }


    }

    fun option3(view: View){

        if (pastQuestions.size == 250){
            val intent = Intent(this@SpeedRunActivity, FinishActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }

        else if (countryData.data[currentFlag.toInt()].name == binding.option3.text) {
            newQuestion()
            increaseScore()
        }
        else{
            wrongAnswer()
            wrongAnswerCounter()
        }


    }

    fun option4(view: View){

        if (pastQuestions.size == 250){
            val intent = Intent(this@SpeedRunActivity, FinishActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }

        else if (countryData.data[currentFlag.toInt()].name == binding.option4.text) {
            newQuestion()
            increaseScore()
        }
        else{
            wrongAnswer()
            wrongAnswerCounter()
        }


    }


    private fun wrongAnswer(){
        newQuestion()
    }

    private fun wrongAnswerCounter(){
        wrongAnswerCount--
        if (wrongAnswerCount == 0){
            binding.cross1.visibility = View.INVISIBLE
            Toast.makeText(this@SpeedRunActivity, "Game Over", Toast.LENGTH_LONG).show()
            val intent = Intent(this@SpeedRunActivity, MainActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
            finish()

        }
        else if (wrongAnswerCount == 1){
            binding.cross2.visibility = View.INVISIBLE
        }
        else if (wrongAnswerCount == 2){
            binding.cross3.visibility = View.INVISIBLE
        }


    }

    fun increaseScore (){
        score++
        binding.scoreText.text = "Score: ${score}"
    }


}