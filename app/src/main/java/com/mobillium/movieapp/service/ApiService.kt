package com.mobillium.movieapp.service

import com.mobillium.movieapp.BuildConfig
import com.mobillium.movieapp.model.MoviesResponse
import com.mobillium.movieapp.model.Results
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): Single<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcoming(
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): Single<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): Single<Results>
}