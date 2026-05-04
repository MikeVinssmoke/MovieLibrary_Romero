package com.example.romero.movielibrary_romero.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.romero.movielibrary_romero.R
import com.example.romero.movielibrary_romero.databinding.FragmentMovieDetailBinding
import com.example.romero.movielibrary_romero.viewmodel.MovieViewModel

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieDetailBinding.bind(view)

        // Cargar película
        viewModel.loadMovie(args.movieId)

        // LISTENERS (solo una vez)
        binding.switchWatched.setOnCheckedChangeListener { _, isChecked ->
            viewModel.selectedMovie.value?.let { movie ->
                if (movie.watched != isChecked) {
                    viewModel.toggleWatched(movie)
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            viewModel.selectedMovie.value?.let { movie ->
                val action = MovieDetailFragmentDirections
                    .actionDetailToEdit(movieId = movie.id)
                findNavController().navigate(action)
            }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.selectedMovie.value?.let { movie ->
                viewModel.delete(movie)
                findNavController().popBackStack()
            }
        }

        // OBSERVE (solo UI)
        viewModel.selectedMovie.observe(viewLifecycleOwner) { movie ->
            movie ?: return@observe

            binding.tvDetailTitle.text = movie.title
            binding.tvDetailYear.text = "Año: ${movie.year}"
            binding.tvDetailGenre.text = "Género: ${movie.genre}"
            binding.tvDetailRating.text = "Rating: ${movie.rating}/10"

            // Evitar que el listener se dispare solo
            binding.switchWatched.setOnCheckedChangeListener(null)
            binding.switchWatched.isChecked = movie.watched
            binding.switchWatched.setOnCheckedChangeListener { _, isChecked ->
                if (movie.watched != isChecked) {
                    viewModel.toggleWatched(movie)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}