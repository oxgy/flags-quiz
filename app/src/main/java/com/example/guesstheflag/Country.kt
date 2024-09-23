package com.example.guesstheflag

data class Country(
    val code: String,
    val unicode: String,
    val name: String,
    val flag: String
)

data class CountryData(
    val data: List<Country>
)

