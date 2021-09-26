package com.mobillium.movieapp.di.module

import androidx.lifecycle.ViewModel
import com.mobillium.movieapp.di.ViewModelKey
import com.mobillium.movieapp.viewmodel.MovieDetailViewModel
import com.mobillium.movieapp.viewmodel.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
   @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    internal abstract fun bindMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel

}