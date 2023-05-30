package com.edgar.interview.screens.popular.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_tv_shows")
data class FavoriteTvShows(
    @PrimaryKey(autoGenerate = false)
    val id: Double = 0.0,
    @ColumnInfo
    val name: String = "",
    @ColumnInfo
    val release_date: String = "",
    @ColumnInfo
    val vote_average: Double = 0.0,
    @ColumnInfo
    val vote_count: Double = 0.0,
    @ColumnInfo
    val popularity: Double = 0.0,
    @ColumnInfo
    val poster_path: String = "",
    @ColumnInfo
    var page: Int = 0,

    var isFavorite: Boolean = true
){
    fun getPoster() = "https://image.tmdb.org/t/p/original/$poster_path"
}