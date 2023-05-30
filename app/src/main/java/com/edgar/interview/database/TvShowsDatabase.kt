package com.edgar.interview.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edgar.interview.database.dao.FavoriteTvShowsDao
import com.edgar.interview.database.dao.PopularTvShowsDao
import com.edgar.interview.database.dao.RemoteKeysDao
import com.edgar.interview.network.models.response.TvShows
import com.edgar.interview.screens.popular.entities.FavoriteTvShows
import com.edgar.interview.screens.popular.paging.RemoteKeys
import com.google.gson.Gson

@Database(
    entities = [TvShows::class, RemoteKeys::class, FavoriteTvShows::class],
    version = 7,
)
abstract class TvShowsDatabase: RoomDatabase() {
    abstract fun getPopularTvShowsDao(): PopularTvShowsDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
    abstract fun getFavoriteTvShowsDao(): FavoriteTvShowsDao

    suspend fun addToFavorites(id: Double, isChecked: Boolean){
        val tvShow = getPopularTvShowsDao().findTvShowById(id)
        getPopularTvShowsDao().update(tvShow.apply { isFavorite = isChecked })

        if(isChecked){
            val favoriteTvShow = Gson().fromJson(tvShow.toJson(), FavoriteTvShows::class.java)
            getFavoriteTvShowsDao().insertFavoriteTvShow(favoriteTvShow)
        }else{
            getFavoriteTvShowsDao().deleteFavoriteTvShowById(tvShow.id)
        }
    }

}