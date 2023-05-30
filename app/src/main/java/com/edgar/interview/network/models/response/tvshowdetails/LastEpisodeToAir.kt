package com.edgar.interview.network.models.response.tvshowdetails

data class LastEpisodeToAir(
    var air_date: String,
    var episode_number: Double,
    var id: Double,
    var name: String,
    var overview: String,
    var production_code: String,
    var runtime: Double,
    var season_number: Double,
    var show_id: Double,
    var still_path: String,
    var vote_average: Double,
    var vote_count: Double
)