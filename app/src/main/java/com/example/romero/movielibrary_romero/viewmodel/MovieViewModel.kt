package com.example.romero.movielibrary_romero.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.romero.movielibrary_romero.data.db.AppDatabase
import com.example.romero.movielibrary_romero.data.repository.MovieRepository
import com.example.romero.movielibrary_romero.model.Movie
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: MovieRepository
    lateinit var movies: LiveData<List<Movie>>

    private val _selectedMovieId = MutableLiveData<Int>()
    val selectedMovie: LiveData<Movie?> = _selectedMovieId.switchMap { id ->
        repository.getMovie(id)
    }

    init {
        val dao = AppDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(dao)
        movies = repository.movies
    }

    fun loadMovie(id: Int) {
        _selectedMovieId.value = id
    }

    fun insert(movie: Movie) = viewModelScope.launch {
        repository.insert(movie)
    }

    fun update(movie: Movie) = viewModelScope.launch {
        repository.update(movie)
    }

    fun delete(movie: Movie) = viewModelScope.launch {
        repository.delete(movie)
    }

    fun toggleWatched(movie: Movie) = viewModelScope.launch {
        repository.update(movie.copy(watched = !movie.watched))
    }
}