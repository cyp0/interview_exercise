package com.edgar.interview.network.models.response.seasondetails

data class Episode(
    var air_date: String,
    var crew: List<Crew>,
    var episode_number: Int,
    var guest_stars: List<GuestStar>,
    var id: Int,
    var name: String,
    var overview: String,
    var production_code: String,
    var runtime: Int,
    var season_number: Int,
    var show_id: Int,
    var still_path: String,
    var vote_average: Double,
    var vote_count: Int
){
    fun getPoster() = "https://image.tmdb.org/t/p/original/$still_path"
}