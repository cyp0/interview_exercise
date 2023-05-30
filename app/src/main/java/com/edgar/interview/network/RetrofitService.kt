package com.edgar.interview.network

import com.edgar.interview.network.models.response.OmdbTvShow
import com.edgar.interview.network.models.response.ResponseTvShows
import com.edgar.interview.network.models.response.episodedetails.EpisodeDetails
import com.edgar.interview.network.models.response.seasondetails.SeasonDetails
import com.edgar.interview.network.models.response.tvshowdetails.TvShowDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZTZlNjBkNTA1MTM4YTA0ZjUyZjVhMTFmZTU5N2Q5NyIsInN1YiI6IjY0NzUwZGQxYmJjYWUwMDBjMTQ0NjhjZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gE6_QYKQLscOrga_IY_jy3N6Bi8GV4rhnhP8Ec3sJ8I"
    )
    @GET("https://api.themoviedb.org/3/discover/tv?&include_null_first_air_dates=false&language=en-US")
    suspend fun getPopularTvShows(
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String
    ): Response<ResponseTvShows>

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZTZlNjBkNTA1MTM4YTA0ZjUyZjVhMTFmZTU5N2Q5NyIsInN1YiI6IjY0NzUwZGQxYmJjYWUwMDBjMTQ0NjhjZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gE6_QYKQLscOrga_IY_jy3N6Bi8GV4rhnhP8Ec3sJ8I"
    )
    @GET("https://api.themoviedb.org/3/tv/{series_id}?language=en-US")
    suspend fun getTvShowDetails(
        @Path("series_id") series_id: Int
    ): Response<TvShowDetails>

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZTZlNjBkNTA1MTM4YTA0ZjUyZjVhMTFmZTU5N2Q5NyIsInN1YiI6IjY0NzUwZGQxYmJjYWUwMDBjMTQ0NjhjZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gE6_QYKQLscOrga_IY_jy3N6Bi8GV4rhnhP8Ec3sJ8I"
    )
    @GET("https://api.themoviedb.org/3/tv/{series_id}/season/{season_number}?language=en-US")
    suspend fun getSeasonDetails(
        @Path("series_id") series_id: Int,
        @Path("season_number") season_number: Int
    ): Response<SeasonDetails>

    @GET("https://www.omdbapi.com/?apikey=d29f1c56&")
    suspend fun findTvShowByName(
        @Query("t") tv_show_title: String
    ): Response<OmdbTvShow>

    @GET("https://www.omdbapi.com/?apikey=d29f1c56")
    suspend fun getTvEpisodeDetails(
        @Query("i") imdbId: String,
        @Query("season") season: Int,
        @Query("episode") episode: Int
    ): Response<EpisodeDetails>


}