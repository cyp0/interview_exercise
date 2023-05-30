package com.edgar.interview.network.models.response.seasondetails

data class SeasonDetails(
    var _id: String,
    var air_date: String,
    var episodes: List<Episode>,
    var id: Int,
    var name: String,
    var overview: String,
    var poster_path: String,
    var season_number: Int
)