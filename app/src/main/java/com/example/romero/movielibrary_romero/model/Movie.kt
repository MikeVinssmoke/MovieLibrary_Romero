package com.example.romero.movielibrary_romero.model


data class Movie(
    val id: Int = 0,
    val title: String,
    val year: Int,
    val genre: String,
    val rating: Float,
    val watched: Boolean = false
)