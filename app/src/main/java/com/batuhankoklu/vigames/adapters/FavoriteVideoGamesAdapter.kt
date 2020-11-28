package com.batuhankoklu.vigames.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batuhankoklu.vigames.R
import com.batuhankoklu.vigames.databinding.CellVideoGameFavoriteBinding
import com.batuhankoklu.vigames.listeners.VideoGamesListener
import com.batuhankoklu.vigames.model.VideoGame
import com.batuhankoklu.vigames.model.VideoGamesResponse

class FavoriteVideoGamesAdapter(favoriteGames : ArrayList<VideoGame> , videoGamesListener: VideoGamesListener) : RecyclerView.Adapter<FavoriteVideoGamesAdapter.FavoriteVideoGamesViewHolder>(){

    var layoutInflater : LayoutInflater? = null
    var favoriteGames = favoriteGames
    var videoGamesListener : VideoGamesListener = videoGamesListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVideoGamesViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.context)
        }
        var gameFavoriteBinding : CellVideoGameFavoriteBinding = DataBindingUtil
            .inflate(layoutInflater!! , R.layout.cell_video_game_favorite , parent , false)

        return  FavoriteVideoGamesViewHolder(gameFavoriteBinding)
    }

    override fun getItemCount(): Int {
        return favoriteGames.count()
    }

    fun updateVideoGames(vGames : ArrayList<VideoGame>){
        //this.videoGames = vGames
        favoriteGames.clear()
        favoriteGames.addAll(vGames)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FavoriteVideoGamesViewHolder, position: Int) {
        holder.bindVideoGame(favoriteGames.get(position) , videoGamesListener)
    }

    class FavoriteVideoGamesViewHolder(gameFavoriteBinding: CellVideoGameFavoriteBinding) : RecyclerView.ViewHolder(gameFavoriteBinding.root){

        //Auto generated -> CellVideoGameBinding
        var gameFavoriteBinding : CellVideoGameFavoriteBinding = gameFavoriteBinding

        fun bindVideoGame(videoGame : VideoGame , videoGamesListener: VideoGamesListener){
            gameFavoriteBinding.videoGame = videoGame
            gameFavoriteBinding.executePendingBindings()
            gameFavoriteBinding.root.setOnClickListener {
                videoGamesListener.onVideoGameClicked(videoGame)
            }
        }
    }




}