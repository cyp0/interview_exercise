package com.edgar.interview.screens.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edgar.interview.R
import com.edgar.interview.databinding.ItemTvShowBinding
import com.edgar.interview.network.models.response.TvShows

class TvShowPagingAdapter(val listener: PopularTvShowsAdapterListener) : PagingDataAdapter<TvShows, TvShowPagingAdapter.TvShowViewHolder>(TvShowComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(
            ItemTvShowBinding.inflate(
                LayoutInflater.from(parent.context)
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.itemView.findViewById<CheckBox>(R.id.cbFavorite).setOnCheckedChangeListener { buto, isChecked ->
            snapshot()[position]!!.isFavorite = isChecked
            getItem(position)?.let { listener.updateTvShow(it, isChecked) }
        }
        getItem(position)?.let { holder.bind(it) }
    }


    class TvShowViewHolder(private val binding: ItemTvShowBinding, val listener: PopularTvShowsAdapterListener): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShows){

            with(binding){
                Glide.with(ivShowPoster.context)
                    .load(tvShow.getPoster())
                    .fitCenter()
                    .into(ivShowPoster)

                tvShowTitle.text = tvShow.name
                ratingBar.numStars = 5
                ratingBar.rating = (tvShow.vote_average/2).toFloat()
                tvShowVoteCount.text = tvShow.vote_count.toInt().toString() + " votes"
                cbFavorite.isChecked = tvShow.isFavorite
                card.setOnClickListener {
                    listener.goToTvShowDetails(tvShow.id)
                }
            }
        }
    }

    object TvShowComparator : DiffUtil.ItemCallback<TvShows>(){
        override fun areItemsTheSame(oldItem: TvShows, newItem: TvShows): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShows, newItem: TvShows): Boolean {
            return oldItem == newItem
        }

    }
}