package com.edgar.interview.screens.popular

import com.edgar.interview.network.models.response.TvShows

interface PopularTvShowsAdapterListener {
    fun updateTvShow(tvShow: TvShows, isChecked: Boolean)
    fun goToTvShowDetails(tvShowId: Double)
}