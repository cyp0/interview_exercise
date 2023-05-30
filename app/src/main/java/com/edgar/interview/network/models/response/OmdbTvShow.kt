package com.edgar.interview.network.models.response

import com.edgar.interview.network.models.response.episodedetails.Rating

data class OmdbTvShow(
    val imdbID: String,
    var Actors: String,
    var Awards: String,
    var Country: String,
    var Director: String,
    var Episode: String,
    var Genre: String,
    var Language: String,
    var Metascore: String,
    var Plot: String,
    var Poster: String,
    var Rated: String,
    var Ratings: List<Rating>,
    var Released: String,
    var Response: String,
    var Runtime: String,
    var Season: String,
    var Title: String,
    var Type: String,
    var Writer: String,
    var Year: String,

    )
