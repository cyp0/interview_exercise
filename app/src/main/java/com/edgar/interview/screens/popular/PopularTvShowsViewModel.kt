package com.edgar.interview.screens.popular

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.edgar.interview.database.TvShowsDatabase
import com.edgar.interview.network.RetrofitService
import com.edgar.interview.network.models.response.TvShows
import com.edgar.interview.screens.popular.paging.PopularTvShowsRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularTvShowsViewModel @Inject constructor(
    private val repository: RetrofitService,
    private val tvShowsDatabase: TvShowsDatabase
) : ViewModel(){


    @OptIn(ExperimentalPagingApi::class)
    fun getPopularTvShows(sortBy: String): kotlinx.coroutines.flow.Flow<PagingData<TvShows>> =
        Pager(
            config = PagingConfig(
                pageSize = 60 ,
                prefetchDistance = 30
            ),
            pagingSourceFactory = {
                tvShowsDatabase.getPopularTvShowsDao().getTvShows()
            },
            remoteMediator = PopularTvShowsRemoteMediator(
                repository,
                tvShowsDatabase,
                sortBy
            )
        ).flow

}