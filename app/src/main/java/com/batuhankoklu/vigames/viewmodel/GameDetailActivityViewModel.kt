package com.batuhankoklu.vigames.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batuhankoklu.vigames.model.VideoGameDetail
import com.batuhankoklu.vigames.model.VideoGamesResponse
import com.batuhankoklu.vigames.repository.Repository

class GameDetailActivityViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    lateinit var videoGameDetail : LiveData<VideoGameDetail>

    fun getVideoGameDetail(gameId : String) : LiveData<VideoGameDetail> {
        videoGameDetail = Repository().getVideoGameDetail(gameId)
        return videoGameDetail
    }

}