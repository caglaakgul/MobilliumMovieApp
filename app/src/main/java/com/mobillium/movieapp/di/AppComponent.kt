package com.mobillium.movieapp.di

import android.app.Application
import com.mobillium.movieapp.base.MovieApplication
import com.mobillium.movieapp.di.module.ActivityBuildersModule
import com.mobillium.movieapp.di.module.AppModule
import com.mobillium.movieapp.di.module.NetworkModule
import com.mobillium.movieapp.di.module.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class
    ]
)

interface AppComponent : AndroidInjector<MovieApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}