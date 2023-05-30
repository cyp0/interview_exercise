package com.edgar.interview.screens.tvshowdetails

import com.edgar.interview.screens.episodedetails.TvShowDetails

interface EpisodeAdapterListener {
    fun openEpisodeDetails(tvShowDetails: TvShowDetails)
}