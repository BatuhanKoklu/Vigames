package com.batuhankoklu.vigames.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batuhankoklu.vigames.model.VideoGame
import com.batuhankoklu.vigames.model.VideoGamesResponse
import com.batuhankoklu.vigames.repository.Repository

class HomeFragmentViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var searchString = MutableLiveData<String>()
    lateinit var videoGameResponse : LiveData<VideoGamesResponse>

    fun getVideoGames() : LiveData<VideoGamesResponse> {
        videoGameResponse = Repository().getVideoGames()
        return videoGameResponse
    }

    fun searchVideoGame() : ArrayList<VideoGame>{
        var newList = ArrayList<VideoGame>()
        if (videoGameResponse.value?.videoGameList != null){
            for (i in videoGameResponse.value?.videoGameList!!){
                if (i.name.contains(searchString.value.toString() , true)){
                    newList.add(i)
                }
            }
        }
        return newList
    }

}