package com.example.romero.movielibrary_romero.ui.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.romero.movielibrary_romero.R
import com.example.romero.movielibrary_romero.databinding.FragmentMovieEditBinding
import com.example.romero.movielibrary_romero.model.Movie
import com.example.romero.movielibrary_romero.viewmodel.MovieViewModel

class MovieEditFragment : Fragment(R.layout.fragment_movie_edit) {

    private var _binding: FragmentMovieEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()
    private val args: MovieEditFragmentArgs by navArgs()

    private var currentMovie: Movie? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieEditBinding.bind(view)

        val isEditMode = args.movieId != -1

        if (isEditMode) {
            viewModel.loadMovie(args.movieId)
        }

        // OBSERVER (uno solo)
        viewModel.selectedMovie.observe(viewLifecycleOwner) { movie ->
            movie ?: return@observe
            currentMovie = movie

            binding.etTitle.setText(movie.title)
            binding.etYear.setText(movie.year.toString())
            binding.etGenre.setText(movie.genre)
            binding.etRating.setText(movie.rating.toString())
            binding.switchWatchedEdit.isChecked = movie.watched
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val year = binding.etYear.text.toString().toIntOrNull() ?: 0
            val genre = binding.etGenre.text.toString().trim()
            val rating = binding.etRating.text.toString().toFloatOrNull() ?: 0f
            val watched = binding.switchWatchedEdit.isChecked

            if (title.isEmpty()) {
                binding.etTitle.error = "El título es obligatorio"
                return@setOnClickListener
            }

            if (isEditMode && currentMovie != null) {
                viewModel.update(
                    currentMovie!!.copy(
                        title = title,
                        year = year,
                        genre = genre,
                        rating = rating,
                        watched = watched
                    )
                )
            } else {
                viewModel.insert(
                    Movie(
                        title = title,
                        year = year,
                        genre = genre,
                        rating = rating,
                        watched = watched
                    )
                )
            }

            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}