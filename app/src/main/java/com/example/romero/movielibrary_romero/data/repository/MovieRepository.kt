package com.example.romero.movielibrary_romero.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.romero.movielibrary_romero.data.db.MovieDao
import com.example.romero.movielibrary_romero.data.db.toDomain
import com.example.romero.movielibrary_romero.data.db.toEntity
import com.example.romero.movielibrary_romero.model.Movie

class MovieRepository(private val dao: MovieDao) {

    val movies: LiveData<List<Movie>> = dao.getAllMovies().map { list ->
        list.map { it.toDomain() }
    }

    fun getMovie(id: Int): LiveData<Movie?> =
        dao.getMovieById(id).map { it?.toDomain() }

    suspend fun insert(movie: Movie) = dao.insert(movie.toEntity())

    suspend fun update(movie: Movie) = dao.update(movie.toEntity())

    suspend fun delete(movie: Movie) = dao.delete(movie.toEntity())
}