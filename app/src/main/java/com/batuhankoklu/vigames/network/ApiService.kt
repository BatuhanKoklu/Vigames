package com.batuhankoklu.vigames.network

import com.batuhankoklu.vigames.model.VideoGameDetail
import com.batuhankoklu.vigames.model.VideoGamesResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("x-rapidapi-key: a2ee07dfd4msha1c5be158be690fp1cc1a5jsn3f8ec35ef20c")
    @GET("/games")
    fun getVideoGames(): Call<VideoGamesResponse>

    @Headers("x-rapidapi-key: a2ee07dfd4msha1c5be158be690fp1cc1a5jsn3f8ec35ef20c")
    @GET("/games/{gameId}")
    fun getVideoGameDetail(@Path("gameId") gameId : String): Call<VideoGameDetail>

    /*@GET("coins/{id}")
    fun getCoinDetail(@Path("id") coinName: String): Call<CoinModel>

    @GET("coins/")
    fun getTopCoins(): Call<List<CoinModel>>

    @GET("coins/list")
    fun getAllCoins(): Call<List<CoinModel>>

    @GET("coins/{id}/market_chart?vs_currency=usd&days=6&interval=daily")
    fun getCoinWeeklyPrice(@Path("id") coinName: String): Call<Price7Days>*/


}