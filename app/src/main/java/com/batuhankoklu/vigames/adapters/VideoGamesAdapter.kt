package com.batuhankoklu.vigames.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batuhankoklu.vigames.R
import com.batuhankoklu.vigames.databinding.CellVideoGameBinding
import com.batuhankoklu.vigames.listeners.VideoGamesListener
import com.batuhankoklu.vigames.model.VideoGame
import com.batuhankoklu.vigames.model.VideoGamesResponse

class VideoGamesAdapter(videoGames : ArrayList<VideoGame> , videoGamesListener: VideoGamesListener) : RecyclerView.Adapter<VideoGamesAdapter.VideoGamesViewHolder>(){

    var layoutInflater : LayoutInflater? = null
    var videoGames = videoGames
    var videoGamesListener : VideoGamesListener = videoGamesListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoGamesViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.context)
        }
        var videoGameBinding : CellVideoGameBinding = DataBindingUtil
            .inflate(layoutInflater!! , R.layout.cell_video_game , parent , false)

        return  VideoGamesViewHolder(videoGameBinding)
    }

    override fun getItemCount(): Int {
        return videoGames.count()
    }

    fun updateVideoGames(vGames : ArrayList<VideoGame>){
        //this.videoGames = vGames
        videoGames.clear()
        videoGames.addAll(vGames)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VideoGamesViewHolder, position: Int) {
        holder.bindVideoGame(videoGames.get(position) , videoGamesListener)
    }

    class VideoGamesViewHolder(cellVideoGameBinding: CellVideoGameBinding) : RecyclerView.ViewHolder(cellVideoGameBinding.root){

        //Auto generated -> CellVideoGameBinding
        var cellVideoGameBinding : CellVideoGameBinding = cellVideoGameBinding

        fun bindVideoGame(videoGame : VideoGame , videoGamesListener: VideoGamesListener){
            cellVideoGameBinding.videoGame = videoGame
            cellVideoGameBinding.executePendingBindings()
            cellVideoGameBinding.root.setOnClickListener {
                videoGamesListener.onVideoGameClicked(videoGame)
            }
        }
    }




}