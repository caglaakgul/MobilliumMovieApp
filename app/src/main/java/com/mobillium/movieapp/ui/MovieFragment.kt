package com.mobillium.movieapp.ui

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mobillium.movieapp.R
import com.mobillium.movieapp.databinding.FragmentMovieBinding
import com.mobillium.movieapp.model.Results
import com.mobillium.movieapp.ui.adapter.MovieNowPlayingAdapter
import com.mobillium.movieapp.ui.adapter.MovieUpcomingAdapter
import com.mobillium.movieapp.viewmodel.MovieViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.PagerSnapHelper

import android.view.ViewGroup





class MovieFragment : DaggerFragment(), MovieUpcomingAdapter.OnItemClickListener, MovieNowPlayingAdapter.OnItemNowPlayingClickListener  {
    @Inject
    lateinit var viewModel: MovieViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentMovieBinding

    private var movieUpcomingListAdapter = MovieUpcomingAdapter(arrayListOf(), this)

    private lateinit var resultsUpcoming : Results

    private var movieNowPlayingListAdapter = MovieNowPlayingAdapter(arrayListOf(), this)

    private lateinit var resultsNowPlaying : Results

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refreshData()

        binding.upcomingListRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = movieUpcomingListAdapter
        }

        binding.nowPlayingListRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieNowPlayingListAdapter

        }
        binding.indicator.attachToRecyclerView(binding.nowPlayingListRecyclerView)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.upcomingListRecyclerView.visibility = View.GONE
            binding.nowPlayingListRecyclerView.visibility = View.GONE
            binding.upcomingListError.visibility = View.GONE
            binding.upcomingListProgressBar.visibility = View.VISIBLE
            binding.nowPlayingListError.visibility = View.GONE
            binding.nowPlayingListProgressBar.visibility = View.VISIBLE
            viewModel.refreshData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.resultData.observe(viewLifecycleOwner, Observer { resultData ->
            resultData?.let {
                view?.let {
                    val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(viewModel.overview, viewModel.originalTitle, viewModel.releaseDate,
                        viewModel.voteAverage, viewModel.backdropPath)
                    Navigation.findNavController(it).navigate(action)
                }
            }
        })
        viewModel.movieUpcomingList.observe(viewLifecycleOwner, Observer { movieUpcomingList ->
            movieUpcomingList?.let {
                binding.upcomingListRecyclerView.visibility = View.VISIBLE
                movieUpcomingListAdapter.updateList(movieUpcomingList.results ?: listOf())
                binding.upcomingListProgressBar.visibility = View.GONE
            }
        })
        viewModel.movieUpcomingError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) binding.upcomingListError.visibility = View.VISIBLE
                else binding.upcomingListError.visibility = View.GONE
            }
        })
        viewModel.movieUpcomingLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) binding.upcomingListProgressBar.visibility = View.VISIBLE
                else binding.upcomingListProgressBar.visibility = View.GONE
            }
            binding.upcomingListProgressBar.visibility = View.GONE
        })

        viewModel.movieNowPlayingList.observe(viewLifecycleOwner, Observer { movieNowPlayingList ->
            movieNowPlayingList?.let {
                binding.nowPlayingListRecyclerView.visibility = View.VISIBLE
                movieNowPlayingListAdapter.updateNowPlayingList(movieNowPlayingList.results ?: listOf())
                binding.nowPlayingListProgressBar.visibility = View.GONE
            }
        })
        viewModel.movieNowPlayingError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) binding.nowPlayingListError.visibility = View.VISIBLE
                else binding.nowPlayingListError.visibility = View.GONE
            }
        })
        viewModel.movieNowPlayingLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) binding.nowPlayingListProgressBar.visibility = View.VISIBLE
                else binding.nowPlayingListProgressBar.visibility = View.GONE
            }
            binding.nowPlayingListProgressBar.visibility = View.GONE
        })
    }

    override fun onItemClick(item: Results, position: Int) {
        viewModel.getMovieDetailData(movieUpcomingListAdapter.upcomingList[position].id)
        resultsUpcoming = movieUpcomingListAdapter.upcomingList[position]
    }

    override fun onItemNowPlayingClick(item: Results, position: Int) {
        viewModel.getMovieDetailData(movieNowPlayingListAdapter.nowPlayingList[position].id)
        resultsNowPlaying = movieNowPlayingListAdapter.nowPlayingList[position]
    }
}