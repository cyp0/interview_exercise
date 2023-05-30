package com.edgar.interview.network.models.response.tvshowdetails

data class TvShowDetails(
    var adult: Boolean,
    var backdrop_path: String,
    var created_by: List<CreatedBy>,
    var episode_run_time: List<Int>,
    var first_air_date: String,
    var genres: List<Genre>,
    var homepage: String,
    var id: Int,
    var in_production: Boolean,
    var languages: List<String>,
    var last_air_date: String,
    var last_episode_to_air: LastEpisodeToAir,
    var name: String,
    var networks: List<Network>,
    var next_episode_to_air: Any?,
    var number_of_episodes: Int,
    var number_of_seasons: Int,
    var origin_country: List<String>,
    var original_language: String,
    var original_name: String,
    var overview: String,
    var popularity: Double,
    var poster_path: String,
    var production_companies: List<ProductionCompany>,
    var production_countries: List<ProductionCountry>,
    var seasons: List<Season>,
    var spoken_languages: List<SpokenLanguage>,
    var status: String,
    var tagline: String,
    var type: String,
    var vote_average: Double,
    var vote_count: Int
){
    fun getPoster() = "https://image.tmdb.org/t/p/original/$poster_path"


}