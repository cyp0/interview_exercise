package com.edgar.interview.screens.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edgar.interview.databinding.ItemTvShowBinding
import com.edgar.interview.screens.popular.entities.FavoriteTvShows

class FavoriteTvShowsAdapter(val tvShows: List<FavoriteTvShows>): RecyclerView.Adapter<FavoriteTvShowsAdapter.FavoriteTvShowViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTvShowViewHolder {
        return FavoriteTvShowViewHolder(
            ItemTvShowBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount() = tvShows.count()

    override fun onBindViewHolder(holder: FavoriteTvShowViewHolder, position: Int) {
        holder.bind(tvShows[position])
    }

    inner class FavoriteTvShowViewHolder(val binding: ItemTvShowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tvShow: FavoriteTvShows){

            with(binding){
                Glide.with(ivShowPoster.context)
                    .load(tvShow.getPoster())
                    .fitCenter()
                    .into(ivShowPoster)

                tvShowTitle.text = tvShow.name
                ratingBar.rating = (tvShow.vote_average/2).toFloat()
                tvShowVoteCount.text = tvShow.vote_count.toInt().toString() + " votes"
                llFavorite.visibility = View.GONE
            }
        }
    }
}