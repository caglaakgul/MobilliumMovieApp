package com.mobillium.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobillium.movieapp.model.MoviesResponse
import com.mobillium.movieapp.model.Results
import com.mobillium.movieapp.service.ApiService
import com.mobillium.movieapp.util.getMovieNowPlayingList
import com.mobillium.movieapp.util.getMovieUpcomingList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val disposable = CompositeDisposable()

    val movieUpcomingError = MutableLiveData<Boolean>()
    val movieUpcomingLoading = MutableLiveData<Boolean>()
    var movieUpcomingList = MutableLiveData<MoviesResponse>()
    var resultData = MutableLiveData<Results>()

    val movieNowPlayingError = MutableLiveData<Boolean>()
    val movieNowPlayingLoading = MutableLiveData<Boolean>()
    var movieNowPlayingList = MutableLiveData<MoviesResponse>()

    val overview: String
        get() = resultData.value?.overview ?: ""

    val originalTitle: String
        get() = resultData.value?.originalTitle ?: ""

    val releaseDate: String
        get() = resultData.value?.releaseDate ?: ""

    val voteAverage: String
        get() = resultData.value?.voteAverage.toString()

    val backdropPath: String
        get() = resultData.value?.backdropPath ?: ""


    fun refreshData() {
        getMovieUpcomingList(
            apiService, movieUpcomingList,
            movieUpcomingError,
            movieUpcomingLoading, disposable
        )

        getMovieNowPlayingList(
            apiService, movieNowPlayingList,
            movieNowPlayingError,
            movieNowPlayingLoading, disposable
        )
    }

    fun getMovieDetailData(id: Int) {
        disposable.add(
            apiService.getMovieDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Results>() {
                    override fun onSuccess(t: Results) {
                        resultData.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

}