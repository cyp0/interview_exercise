package com.edgar.interview.screens.tvshowdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edgar.interview.databinding.ItemEpisodesBinding
import com.edgar.interview.network.models.response.seasondetails.Episode
import com.edgar.interview.network.models.response.seasondetails.SeasonDetails
import com.edgar.interview.screens.episodedetails.TvShowDetails

class EpisodesAdapter(val episodes: List<Episode>, val listener: EpisodeAdapterListener): RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        return EpisodesViewHolder(ItemEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun getItemCount() = episodes.count()

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    class EpisodesViewHolder(val binding: ItemEpisodesBinding, val listener: EpisodeAdapterListener): RecyclerView.ViewHolder(binding.root) {
         fun bind(episode: Episode){
             with(binding){
                 tvShowTitle.text = episode.name
                 tvAiredDate.text = "Aired on " + episode.air_date
                 card.setOnClickListener {
                     listener.openEpisodeDetails(TvShowDetails(title = "", seasonNumber = episode.season_number, episodeNumber = episode.episode_number))
                 }
             }
        }
    }
}