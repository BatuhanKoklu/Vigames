package com.batuhankoklu.vigames.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batuhankoklu.vigames.model.VideoGame
import com.batuhankoklu.vigames.model.VideoGameDetail
import com.batuhankoklu.vigames.model.VideoGamesResponse
import com.batuhankoklu.vigames.repository.DBHelper
import com.batuhankoklu.vigames.repository.Repository


class FavoritesFragmentViewModel(context: Context) : ViewModel() {

    private val db by lazy { DBHelper(context) }
    var isLoading = MutableLiveData<Boolean>()
    var favoriteVideoGames = MutableLiveData<ArrayList<VideoGame>>()

    fun getFavorites() : MutableLiveData<ArrayList<VideoGame>> {
        var data = db.readData()
        var arrayList = ArrayList<VideoGame>()
        for (i in data){
            arrayList.add(i)
        }
        favoriteVideoGames.value = arrayList
        return favoriteVideoGames
    }
}