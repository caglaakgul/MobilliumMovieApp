package com.mobillium.movieapp.di.module

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.mobillium.movieapp.BuildConfig
import com.mobillium.movieapp.BuildConfig.BASE_URL
import com.mobillium.movieapp.service.ApiService
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
object NetworkModule {
    @Provides
    @JvmStatic
    internal fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @JvmStatic
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val okhttp = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            okhttp.addNetworkInterceptor(StethoInterceptor())
            okhttp.addInterceptor(ChuckInterceptor(application))
        }
        return okhttp.build()
    }

    @Provides
    @JvmStatic
    internal fun provideRetrofitInterface(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}