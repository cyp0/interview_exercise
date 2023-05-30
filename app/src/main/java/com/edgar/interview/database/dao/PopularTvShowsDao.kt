package com.edgar.interview.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.edgar.interview.network.models.response.TvShows


@Dao
interface PopularTvShowsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvShows: List<TvShows>)

    @Query("Select * From tv_shows Order By page")
    fun getTvShows(): PagingSource<Int, TvShows>

    @Query("Delete From tv_shows")
    suspend fun clearAllTvShows()

    @Query("SELECT * FROM tv_shows WHERE id LIKE :id LIMIT 1")
    fun findTvShowById(id: Double): TvShows

    @Update
    fun update(tvShow: TvShows)
}