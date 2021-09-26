package com.mobillium.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobillium.movieapp.R
import com.mobillium.movieapp.databinding.ItemNowPlayingListBinding
import com.mobillium.movieapp.model.Results

class MovieNowPlayingAdapter(val nowPlayingList: ArrayList<Results>, var clickListener: OnItemNowPlayingClickListener) : RecyclerView.Adapter<MovieNowPlayingAdapter.MovieNowPlayingViewHolder>()  {

    override fun getItemCount(): Int = nowPlayingList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieNowPlayingViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        val binding: ItemNowPlayingListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_now_playing_list, parent, false)
        viewHolder = MovieNowPlayingViewHolder(binding)

        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieNowPlayingViewHolder, position: Int) {
        holder.binding.nowPlayingViewModel = nowPlayingList[position]
        holder.init(nowPlayingList.get(position), clickListener)
    }

    class MovieNowPlayingViewHolder(val binding: ItemNowPlayingListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun init(item: Results, action: OnItemNowPlayingClickListener) {
            binding.root.setOnClickListener {
                action.onItemNowPlayingClick(item, adapterPosition)
            }
        }
    }

    fun updateNowPlayingList(newList: List<Results>) {
        nowPlayingList.clear()
        nowPlayingList.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnItemNowPlayingClickListener {
        fun onItemNowPlayingClick(item: Results, position: Int)
    }
}