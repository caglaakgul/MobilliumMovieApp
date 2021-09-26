package com.mobillium.movieapp.di.module

import com.mobillium.movieapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
        modules = [FragmentBuildersModule::class, ViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity
}