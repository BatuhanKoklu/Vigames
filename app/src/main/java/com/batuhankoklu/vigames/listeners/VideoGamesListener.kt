package com.batuhankoklu.vigames.listeners

import com.batuhankoklu.vigames.model.VideoGame

interface VideoGamesListener {

    fun onVideoGameClicked(videoGame : VideoGame)
}