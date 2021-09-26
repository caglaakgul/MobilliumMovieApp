package com.mobillium.movieapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.mobillium.movieapp.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}