package com.mobillium.movieapp.di.module

import com.mobillium.movieapp.ui.MovieDetailFragment
import com.mobillium.movieapp.ui.MovieFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(modules = arrayOf(ViewModelModule::class))
    abstract fun contributeMovieFragment(): MovieFragment

    @ContributesAndroidInjector(modules = arrayOf(ViewModelModule::class))
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment
}