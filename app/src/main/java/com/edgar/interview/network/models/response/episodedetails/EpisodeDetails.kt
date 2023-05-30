package com.edgar.interview.network.models.response.episodedetails

data class EpisodeDetails(
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
    var Runtime: String? = null,
    var Season: String? = null,
    var Title: String? = null,
    var Type: String,
    var Writer: String,
    var Year: String? = null,
    var imdbID: String,
    var imdbRating: String,
    var imdbVotes: String,
    var seriesID: String
)