package com.edgar.interview.screens.favorites

import com.edgar.interview.database.dao.FavoriteTvShowsDao
import javax.inject.Inject

class FavoriteTvShowsRepository @Inject constructor(private val favoriteTvShowsDao: FavoriteTvShowsDao) {
    suspend fun getAllFavoriteTvShows() = favoriteTvShowsDao.getAll()
}