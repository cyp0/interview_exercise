package com.edgar.interview.screens.episodedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgar.interview.network.RetrofitService
import com.edgar.interview.network.models.response.episodedetails.EpisodeDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    val service: RetrofitService
) : ViewModel() {

    private var _episodeDetails = MutableLiveData<EpisodeDetails>()
    val episodeDetails: LiveData<EpisodeDetails> get() = _episodeDetails

    fun findTvShowByTitle(tvShowDetails: TvShowDetails){
        viewModelScope.launch {
           val imdbId = async {
               service.findTvShowByName(tvShowDetails.title).body()!!.imdbID
           }

            _episodeDetails.value = service.getTvEpisodeDetails(imdbId.await(), tvShowDetails.seasonNumber, tvShowDetails.episodeNumber).body()
        }
    }
}