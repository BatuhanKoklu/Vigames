package com.batuhankoklu.vigames.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.batuhankoklu.cotrack.Helpers.Helpers
import com.batuhankoklu.vigames.R
import com.batuhankoklu.vigames.adapters.VideoGamesAdapter
import com.batuhankoklu.vigames.adapters.ViewPagerAdapter
import com.batuhankoklu.vigames.databinding.FragmentHomeBinding
import com.batuhankoklu.vigames.helpers.StaticVariables
import com.batuhankoklu.vigames.listeners.VideoGamesListener
import com.batuhankoklu.vigames.model.VideoGame
import com.batuhankoklu.vigames.model.VideoGamesResponse
import com.batuhankoklu.vigames.viewmodel.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() , VideoGamesListener {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : HomeFragmentViewModel
    private var videoGames = ArrayList<VideoGame>()
    private var videoGamesAdapter : VideoGamesAdapter? = null
    private var needUpdate = false
    private var helper = Helpers()
    private lateinit var viewPagerAdapter : ViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = initDataBinding(inflater , container)

        initRecyclerView()

        initSearchVideoGame()

        return binding.root
    }

    private fun initDataBinding(inflater: LayoutInflater , container: ViewGroup?) : FragmentHomeBinding{
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_home , container , false)
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding
    }

    fun initRecyclerView(){
        videoGamesAdapter = VideoGamesAdapter(videoGames , this)
        binding.rvVideoGames.setHasFixedSize(true)
        binding.rvVideoGames.layoutManager = LinearLayoutManager(requireContext())
        binding.rvVideoGames.adapter = videoGamesAdapter
        binding.rvVideoGames.visibility = View.VISIBLE
        getVideoGames()
    }

    fun getVideoGames(){
        viewModel.isLoading.value = true
        viewModel.getVideoGames().observe(requireActivity() , Observer<VideoGamesResponse>{ videoGamesResponse ->
            viewModel.isLoading.value = false
            if(videoGamesResponse != null){
                videoGames = videoGamesResponse.videoGameList
                videoGamesAdapter?.updateVideoGames(videoGamesResponse.videoGameList)

                //ilk üç screenshot
                var screenshotList = ArrayList<String>()
                screenshotList.add(videoGamesResponse.videoGameList.get(0).short_screenshots.get(0).image)
                screenshotList.add(videoGamesResponse.videoGameList.get(1).short_screenshots.get(0).image)
                screenshotList.add(videoGamesResponse.videoGameList.get(2).short_screenshots.get(0).image)
                prepareViewPager(screenshotList)


            }
        })
    }

    fun initSearchVideoGame(){
        binding.etxtSearchVideoGame.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(string: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!string.isNullOrEmpty()){
                    if (string?.length!! >= 3){
                        needUpdate = true
                        var searchList = viewModel.searchVideoGame()
                        if(searchList.size > 0){
                            //item bulmuştur listeyi göster updatele
                            videoGamesAdapter?.updateVideoGames(searchList)
                            rvVideoGames.visibility = View.VISIBLE
                            viewPagerConstraintLayoutHome.visibility = View.GONE
                            txtSearchError.visibility = View.GONE
                        }else{
                            //item bulamamıştır error göster
                            rvVideoGames.visibility = View.GONE
                            viewPagerConstraintLayoutHome.visibility = View.GONE
                            txtSearchError.visibility = View.VISIBLE
                        }
                    }else{
                        //normal haline getir
                        rvVideoGames.visibility = View.VISIBLE
                        viewPagerConstraintLayoutHome.visibility = View.VISIBLE
                        txtSearchError.visibility = View.GONE
                        if (needUpdate){
                            videoGamesAdapter?.updateVideoGames(videoGames)
                            needUpdate = false
                        }
                    }
                }
            }

        })
    }

    fun prepareViewPager(imageList : ArrayList<String>){

        if (fragmentManager != null){
            //fragmentManager kesinlikle kullanılmamlı, fragment reload olunca imagelar gelmez
            viewPagerAdapter = ViewPagerAdapter(childFragmentManager!!)

            if(!imageList.isNullOrEmpty()){
                for (i in imageList){
                    viewPagerAdapter.addFrag(GeneralViewPager(i), i)
                }
            }
        }

        viewpagerGalleryHome.adapter = viewPagerAdapter
        viewPagerConstraintLayoutHome.visibility = View.VISIBLE

        spring_dots_indicator_Home.setViewPager(viewpagerGalleryHome)

    }


    //CELL CLICK
    override fun onVideoGameClicked(videoGame: VideoGame) {
        if (helper.isOnline(requireContext())) {
            StaticVariables.videoGame = videoGame
            helper.redirectFromTo(requireContext() , GameDetailActivity::class.java)
        }
    }
}