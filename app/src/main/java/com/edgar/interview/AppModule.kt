package com.edgar.interview

import android.content.Context
import androidx.room.Room
import com.edgar.interview.database.TvShowsDatabase
import com.edgar.interview.database.dao.FavoriteTvShowsDao
import com.edgar.interview.database.dao.PopularTvShowsDao
import com.edgar.interview.database.dao.RemoteKeysDao
import com.edgar.interview.network.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{


    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient()  = OkHttpClient.Builder()
    .addNetworkInterceptor(interceptor) // same for .addInterceptor(...)
    .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
    .writeTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://localhost/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(RetrofitService::class.java)

    @Singleton
    @Provides
    fun provideTvShowDatabase(@ApplicationContext context: Context): TvShowsDatabase =
        Room
            .databaseBuilder(context, TvShowsDatabase::class.java, "tv_shows_database")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideTvShowsDao(tvShowsDatabase: TvShowsDatabase): PopularTvShowsDao = tvShowsDatabase.getPopularTvShowsDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(tvShowsDatabase: TvShowsDatabase): RemoteKeysDao = tvShowsDatabase.getRemoteKeysDao()

    @Singleton
    @Provides
    fun provideFavoriteTvShowsDao(tvShowsDatabase: TvShowsDatabase): FavoriteTvShowsDao = tvShowsDatabase.getFavoriteTvShowsDao()


}