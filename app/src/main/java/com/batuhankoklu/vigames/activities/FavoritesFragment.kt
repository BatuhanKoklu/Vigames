package com.batuhankoklu.vigames.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.batuhankoklu.cotrack.Helpers.Helpers
import com.batuhankoklu.vigames.R
import com.batuhankoklu.vigames.adapters.FavoriteVideoGamesAdapter
import com.batuhankoklu.vigames.adapters.VideoGamesAdapter
import com.batuhankoklu.vigames.databinding.FragmentFavoritesBinding
import com.batuhankoklu.vigames.factory.FavoritesFragmentViewModelFactory
import com.batuhankoklu.vigames.helpers.StaticVariables
import com.batuhankoklu.vigames.listeners.VideoGamesListener
import com.batuhankoklu.vigames.model.VideoGame
import com.batuhankoklu.vigames.model.VideoGamesResponse
import com.batuhankoklu.vigames.viewmodel.FavoritesFragmentViewModel

class FavoritesFragment : Fragment() , VideoGamesListener {

    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var viewModel : FavoritesFragmentViewModel
    private var favoriteGames = ArrayList<VideoGame>()
    private var favoriteGamesAdapter : FavoriteVideoGamesAdapter? = null
    private var helper = Helpers()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = initDataBinding(inflater , container)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initDataBinding(inflater: LayoutInflater , container: ViewGroup?) : FragmentFavoritesBinding {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_favorites , container , false)
        var viewModelFactory = FavoritesFragmentViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesFragmentViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding
    }

    fun initRecyclerView(){
        favoriteGamesAdapter = FavoriteVideoGamesAdapter(favoriteGames , this)
        binding.rvFavorites.setHasFixedSize(true)
        binding.rvFavorites.layoutManager = GridLayoutManager(requireContext() , 2)
        binding.rvFavorites.adapter = favoriteGamesAdapter
        getFavoriteGames()
    }

    fun getFavoriteGames(){
        viewModel.isLoading.value = true
        viewModel.getFavorites().observe(requireActivity() , Observer<ArrayList<VideoGame>>{ favoriteVideoGames ->
            viewModel.isLoading.value = false
            if(favoriteGames != null){
                favoriteGames = favoriteVideoGames
                favoriteGamesAdapter?.updateVideoGames(favoriteVideoGames)
            }
        })
    }

    override fun onVideoGameClicked(videoGame: VideoGame) {
        if (helper.isOnline(requireContext())){
            StaticVariables.videoGame = videoGame
            helper.redirectFromTo(requireContext() , GameDetailActivity::class.java)
        }
    }


}