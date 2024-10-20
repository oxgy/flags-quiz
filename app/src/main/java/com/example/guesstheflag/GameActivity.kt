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
import com.google.gson.Gson
import java.io.InputStreamReader


class GameActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGameBinding
    private lateinit var countryData: CountryData
    private lateinit var currentFlag: String
    private var randomOption: Int? = null
    private var wrongAnswerCount = 3
    var score: Int = 0
    val pastQuestions = ArrayList<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGameBinding.inflate(layoutInflater)
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

        binding.option1.setBackgroundColor(Color.rgb(113,111,255))
        binding.option2.setBackgroundColor(Color.rgb(113,111,255))
        binding.option3.setBackgroundColor(Color.rgb(113,111,255))
        binding.option4.setBackgroundColor(Color.rgb(113,111,255))


        binding.option1.setEnabled(true)
        binding.option2.setEnabled(true)
        binding.option3.setEnabled(true)
        binding.option4.setEnabled(true)

        binding.option1.setTextColor(Color.WHITE)
        binding.option2.setTextColor(Color.WHITE)
        binding.option3.setTextColor(Color.WHITE)
        binding.option4.setTextColor(Color.WHITE)


    }

    private fun nQDelay(){
        Handler().postDelayed({
            newQuestion()
        },1000)


    }

    fun option1(view: View){
        disableButtons()
        binding.option1.setTextColor(Color.BLACK)

        if (pastQuestions.size == 250){
            val intent = Intent(this@GameActivity, FinishActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }

        else if (countryData.data[currentFlag.toInt()].name == binding.option1.text) {
            binding.option1.setBackgroundColor(Color.rgb(100,255,100))

            nQDelay()
            increaseScore()
        }
        else{
            binding.option1.setBackgroundColor(Color.rgb(255,87,51))
            wrongAnswer()
            wrongAnswerCounter()
        }


    }

    fun option2(view: View){
        disableButtons()
        binding.option2.setTextColor(Color.BLACK)

        if (pastQuestions.size == 250){
            val intent = Intent(this@GameActivity, FinishActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }

        else if (countryData.data[currentFlag.toInt()].name == binding.option2.text) {
            binding.option2.setBackgroundColor(Color.rgb(100,255,100))
            nQDelay()
            increaseScore()
        }
        else{
            binding.option2.setBackgroundColor(Color.rgb(255,87,51))
            wrongAnswer()
            wrongAnswerCounter()
        }


    }

    fun option3(view: View){
        disableButtons()
        binding.option3.setTextColor(Color.BLACK)

        if (pastQuestions.size == 250){
            val intent = Intent(this@GameActivity, FinishActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }

        else if (countryData.data[currentFlag.toInt()].name == binding.option3.text) {
            binding.option3.setBackgroundColor(Color.rgb(100,255,100))
            nQDelay()
            increaseScore()
        }
        else{
            binding.option3.setBackgroundColor(Color.rgb(255,87,51))
            wrongAnswer()
            wrongAnswerCounter()
        }


    }

    fun option4(view: View){
        disableButtons()
        binding.option4.setTextColor(Color.BLACK)

        if (pastQuestions.size == 250){
            val intent = Intent(this@GameActivity, FinishActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }

        else if (countryData.data[currentFlag.toInt()].name == binding.option4.text) {
            binding.option4.setBackgroundColor(Color.rgb(100,255,100))
            nQDelay()
            increaseScore()
        }
        else{
            binding.option4.setBackgroundColor(Color.rgb(255,87,51))
            wrongAnswer()
            wrongAnswerCounter()
        }


    }

    private fun disableButtons(){
        binding.option1.setEnabled(false)
        binding.option2.setEnabled(false)
        binding.option3.setEnabled(false)
        binding.option4.setEnabled(false)
    }

    private fun wrongAnswer(){
        changeCorrectAnswerToGreen()
        nQDelay()
    }

    private fun wrongAnswerCounter(){
        wrongAnswerCount--
        if (wrongAnswerCount == 0){
            binding.cross1.visibility = View.INVISIBLE
            Toast.makeText(this@GameActivity, "Game Over", Toast.LENGTH_LONG).show()
            val intent = Intent(this@GameActivity, MainActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)

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

    fun changeCorrectAnswerToGreen(){
        when(randomOption){
            1 -> {binding.option1.setBackgroundColor(Color.rgb(100,255,100))
                binding.option1.setTextColor(Color.BLACK)}
            2 -> {binding.option2.setBackgroundColor(Color.rgb(100,255,100))
                binding.option2.setTextColor(Color.BLACK)}
            3 -> {binding.option3.setBackgroundColor(Color.rgb(100,255,100))
                binding.option3.setTextColor(Color.BLACK)}
            4 -> {binding.option4.setBackgroundColor(Color.rgb(100,255,100))
                binding.option4.setTextColor(Color.BLACK)}

        }
    }
}