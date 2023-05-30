package com.edgar.interview.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgar.interview.database.dao.FavoriteTvShowsDao
import com.edgar.interview.screens.popular.entities.FavoriteTvShows
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteTvShowsViewModel @Inject constructor(
    private val favoriteTvShowsDao: FavoriteTvShowsDao
) : ViewModel() {

    private val _favoriteTvShows = MutableStateFlow(mutableListOf<FavoriteTvShows>())
    val favoriteTvShows = _favoriteTvShows.asStateFlow()

    fun getFavoriteTvShows(){
        _favoriteTvShows.value = mutableListOf()
        viewModelScope.launch(Dispatchers.IO) {
            favoriteTvShowsDao.getAll().asFlow().collectLatest{
                _favoriteTvShows.value.add(it)
            }
        }
    }

}