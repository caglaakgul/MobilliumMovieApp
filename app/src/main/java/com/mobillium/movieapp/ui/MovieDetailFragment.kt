package com.mobillium.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mobillium.movieapp.databinding.FragmentMovieDetailBinding
import com.mobillium.movieapp.viewmodel.MovieDetailViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import androidx.activity.OnBackPressedCallback
import com.mobillium.movieapp.R


class MovieDetailFragment : DaggerFragment() {

    val args: MovieDetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: MovieDetailViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)

        arguments?.let {
            viewModel.args = args
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    view?.let {
                        val action =
                            MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieFragment()
                        Navigation.findNavController(it).navigate(action)
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailViewModel = viewModel

    }
}