package com.batuhankoklu.vigames.model

import com.google.gson.annotations.SerializedName

data class VideoGamesResponse(
    @SerializedName("results") val videoGameList : ArrayList<VideoGame>
)

data class VideoGame(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("released") val released : String,
    @SerializedName("background_image") val background_image : String,
    @SerializedName("rating") val rating : Double,
    @SerializedName("short_screenshots") val short_screenshots : ArrayList<ScreenshotModel>
)

data class VideoGameDetail(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("description_raw") val description : String,
    @SerializedName("released") val released : String,
    @SerializedName("background_image") val background_image : String,
    @SerializedName("website") val website : String,
    @SerializedName("rating") val rating : Double,
    @SerializedName("ratings") val ratings : ArrayList<RatingModel>
)

data class RatingModel(
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("percent") val precent : Double
)

data class ScreenshotModel(
    @SerializedName("id") val id : Int,
    @SerializedName("image") val image : String
)
