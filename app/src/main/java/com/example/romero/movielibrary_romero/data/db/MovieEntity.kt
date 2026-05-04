package com.example.romero.movielibrary_romero.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.romero.movielibrary_romero.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val year: Int,
    val genre: String,
    val rating: Float,
    val watched: Boolean
)

fun MovieEntity.toDomain(): Movie =
    Movie(id, title, year, genre, rating, watched)

fun Movie.toEntity(): MovieEntity =
    MovieEntity(id, title, year, genre, rating, watched)