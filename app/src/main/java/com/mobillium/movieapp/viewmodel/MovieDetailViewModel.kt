package com.mobillium.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import com.mobillium.movieapp.service.ApiService
import com.mobillium.movieapp.ui.MovieDetailFragmentArgs
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    var args: MovieDetailFragmentArgs? = null

    val overview: String
        get() = args?.overview ?: ""

    val originalTitle: String
        get() = args?.originalTitle ?: ""

    val releaseDate: String
        get() = args?.releaseDate ?: ""

    val voteAverage: String
        get() = args?.voteAverage.toString()

    val backdropPath: String
        get() = args?.backdropPath ?: ""

}