package com.edgar.interview.network.models.response.tvshowdetails

data class Season(
    var air_date: String,
    var episode_count: Int,
    var id: Int,
    var name: String,
    var overview: String,
    var poster_path: String,
    var season_number: Int
){
    override fun toString(): String {
        return "Season $season_number"
    }
}