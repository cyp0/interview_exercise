package com.edgar.interview.screens.episodedetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowDetails(
    var title: String,
    val seasonNumber: Int,
    val episodeNumber: Int
): Parcelable
