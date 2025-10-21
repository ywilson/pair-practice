package com.example.practice.characters

data class Character(
    val name: String = "",
    val image: String = "",
    val status: String = "",
    val gender: String = "",
    val species: String = "",
    val type : String = "",
    val origin : Location = Location(),
    val location: Location = Location(),
    val episodes: List<Episode> = listOf(Episode())
)

data class Location(
    val name : String = "",
    val type : String = "",
    val dimension: String = ""
)

data class Episode(
    val name : String = "",
    val episode: String = ""
)
