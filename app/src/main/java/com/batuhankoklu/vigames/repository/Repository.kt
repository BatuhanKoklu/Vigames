package com.batuhankoklu.vigames.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.batuhankoklu.vigames.model.VideoGameDetail
import com.batuhankoklu.vigames.model.VideoGamesResponse
import com.batuhankoklu.vigames.network.ApiClient
import com.batuhankoklu.vigames.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    //When you don't want your data to be modified use LiveData If you want to modify your data later use MutableLiveData

    private var apiService = ApiClient.getClient().create(ApiService::class.java)

    fun getVideoGames() : LiveData<VideoGamesResponse>{

        var data = MutableLiveData<VideoGamesResponse>()

        apiService.getVideoGames().enqueue(object : Callback<VideoGamesResponse> {
            override fun onFailure(call: Call<VideoGamesResponse>?, t: Throwable?) {
                data.value = null
            }

            override fun onResponse(call: Call<VideoGamesResponse>?, response: Response<VideoGamesResponse>?) {
                data.value = response?.body()
            }

        })
        return data
    }

    fun getVideoGameDetail(gameId : String) : LiveData<VideoGameDetail>{

        var data = MutableLiveData<VideoGameDetail>()

        apiService.getVideoGameDetail(gameId).enqueue(object : Callback<VideoGameDetail> {
            override fun onFailure(call: Call<VideoGameDetail>?, t: Throwable?) {
                data.value = null
            }

            override fun onResponse(call: Call<VideoGameDetail>?, response: Response<VideoGameDetail>?) {
                data.value = response?.body()
            }

        })
        return data
    }
}