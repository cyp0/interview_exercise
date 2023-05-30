package com.edgar.interview.screens.popular.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.edgar.interview.database.TvShowsDatabase
import com.edgar.interview.network.RetrofitService
import com.edgar.interview.network.models.response.TvShows
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PopularTvShowsRemoteMediator(
    private val retrofitService: RetrofitService,
    private val tvShowsDatabase: TvShowsDatabase,
    private val sort_by: String
): RemoteMediator<Int, TvShows>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, TvShows>): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

        }

        try {
            val apiResponse = retrofitService.getPopularTvShows(page, sort_by)

            val tvShows = apiResponse.body()!!.results
            val endOfPaginationReached = tvShows.isEmpty()

            tvShowsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    tvShowsDatabase.getRemoteKeysDao().clearRemoteKeys()
                    tvShowsDatabase.getPopularTvShowsDao().clearAllTvShows()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = tvShows.map {
                    RemoteKeys(tvShowId = it.id, prevKey = prevKey, currentPage = page, nextKey = nextKey)
                }

                tvShowsDatabase.getRemoteKeysDao().insertAll(remoteKeys)
                tvShowsDatabase.getPopularTvShowsDao().insertAll(tvShows.onEachIndexed { _, tvShow -> tvShow.page = page })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (tvShowsDatabase.getRemoteKeysDao().getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TvShows>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                tvShowsDatabase.getRemoteKeysDao().getRemoteKeyByTvShowId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TvShows>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { tvShow ->
            tvShowsDatabase.getRemoteKeysDao().getRemoteKeyByTvShowId(tvShow.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TvShows>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { tvShow ->
            tvShowsDatabase.getRemoteKeysDao().getRemoteKeyByTvShowId(tvShow.id)
        }
    }


}