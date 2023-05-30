package com.edgar.interview.screens.tvshowdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgar.interview.network.RetrofitService
import com.edgar.interview.network.models.response.seasondetails.SeasonDetails
import com.edgar.interview.network.models.response.tvshowdetails.TvShowDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    val service: RetrofitService
) : ViewModel() {

    private var tvShowId: Int = 0

    private var _tvShowDetails = MutableLiveData<TvShowDetails>()
    val tvShowDetails: LiveData<TvShowDetails> get() = _tvShowDetails

    private var _seasonDetails = MutableLiveData<SeasonDetails>()
    val seasonDetails: LiveData<SeasonDetails> get() = _seasonDetails


    fun getTvShowDetails(tvShowId: Double){
        this.tvShowId = tvShowId.toInt()
        viewModelScope.launch {
            _tvShowDetails.value = service.getTvShowDetails(this@TvShowDetailsViewModel.tvShowId).body()
            tvShowDetails.value?.seasons?.get(0)?.let { getSeasonDetails(it.season_number) }
        }
    }

    fun getSeasonDetails(seasonNumber: Int){
        viewModelScope.launch {
            _seasonDetails.value = service.getSeasonDetails(tvShowId, seasonNumber).body()
        }
    }

}