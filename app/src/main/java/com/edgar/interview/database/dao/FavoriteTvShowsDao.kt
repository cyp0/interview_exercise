package com.edgar.interview.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edgar.interview.screens.popular.entities.FavoriteTvShows

@Dao
interface FavoriteTvShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTvShow(favoriteTvShows: FavoriteTvShows)

    @Query("SELECT * FROM favorite_tv_shows")
    fun getAll(): List<FavoriteTvShows>

    @Query("DELETE FROM favorite_tv_shows WHERE id = :favoriteTvShowId")
    fun deleteFavoriteTvShowById(favoriteTvShowId: Double)
}