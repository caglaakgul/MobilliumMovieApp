package com.mobillium.movieapp.util

import androidx.lifecycle.MutableLiveData
import com.mobillium.movieapp.model.MoviesResponse
import com.mobillium.movieapp.model.Results
import com.mobillium.movieapp.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

fun getMovieUpcomingList(
    apiService: ApiService,
    movieList: MutableLiveData<MoviesResponse>,
    movieError: MutableLiveData<Boolean>,
    movieLoading: MutableLiveData<Boolean>,
    disposable: CompositeDisposable
) {
    movieLoading.value = true
    disposable.add(
        apiService.getUpcoming()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MoviesResponse>() {
                override fun onSuccess(t: MoviesResponse) {
                    movieList.value = t
                    movieError.value = false
                    movieLoading.value = false
                }

                override fun onError(e: Throwable) {
                    movieError.value = true
                    movieLoading.value = false
                    e.printStackTrace()
                }
            })
    )
}

fun getMovieNowPlayingList(
    apiService: ApiService,
    movieList: MutableLiveData<MoviesResponse>,
    movieError: MutableLiveData<Boolean>,
    movieLoading: MutableLiveData<Boolean>,
    disposable: CompositeDisposable
) {
    movieLoading.value = true
    disposable.add(
        apiService.getNowPlaying()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MoviesResponse>() {
                override fun onSuccess(t: MoviesResponse) {
                    movieList.value = t
                    movieError.value = false
                    movieLoading.value = false
                }

                override fun onError(e: Throwable) {
                    movieError.value = true
                    movieLoading.value = false
                    e.printStackTrace()
                }
            })
    )
}