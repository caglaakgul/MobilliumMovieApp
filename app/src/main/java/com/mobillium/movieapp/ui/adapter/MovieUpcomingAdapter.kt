package com.mobillium.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobillium.movieapp.R
import com.mobillium.movieapp.databinding.ItemUpcomingListBinding
import com.mobillium.movieapp.model.Results

class MovieUpcomingAdapter(val upcomingList: ArrayList<Results>, var clickListener: OnItemClickListener) : RecyclerView.Adapter<MovieUpcomingAdapter.MovieUpcomingViewHolder>() {
    override fun getItemCount(): Int = upcomingList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieUpcomingViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        val binding: ItemUpcomingListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_upcoming_list, parent, false)
        viewHolder = MovieUpcomingViewHolder(binding)

        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieUpcomingViewHolder, position: Int) {
        holder.binding.upcomingViewModel = upcomingList[position]
        holder.init(upcomingList.get(position), clickListener)
    }

    class MovieUpcomingViewHolder(val binding: ItemUpcomingListBinding) :
        RecyclerView.ViewHolder(binding.root) {
         fun init(item: Results, action: OnItemClickListener) {
             binding.root.setOnClickListener {
                 action.onItemClick(item, adapterPosition)
             }
         }
    }

    fun updateList(newList: List<Results>) {
        upcomingList.clear()
        upcomingList.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: Results, position: Int)
    }

}