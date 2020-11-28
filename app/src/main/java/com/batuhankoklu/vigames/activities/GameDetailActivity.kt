package com.batuhankoklu.vigames.activities

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batuhankoklu.cotrack.Helpers.Helpers
import com.batuhankoklu.vigames.R
import com.batuhankoklu.vigames.adapters.ViewPagerAdapter
import com.batuhankoklu.vigames.databinding.ActivityGameDetailBinding
import com.batuhankoklu.vigames.helpers.StaticVariables
import com.batuhankoklu.vigames.model.ScreenshotModel
import com.batuhankoklu.vigames.model.VideoGame
import com.batuhankoklu.vigames.model.VideoGameDetail
import com.batuhankoklu.vigames.viewmodel.GameDetailActivityViewModel
import kotlinx.android.synthetic.main.activity_game_detail.*
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginTop
import com.batuhankoklu.vigames.model.RatingModel
import com.batuhankoklu.vigames.repository.DBHelper

class GameDetailActivity : AppCompatActivity() {

    private lateinit var viewPagerAdapter : ViewPagerAdapter
    private lateinit var binding : ActivityGameDetailBinding
    private lateinit var viewModel : GameDetailActivityViewModel
    private lateinit var videoGame : VideoGameDetail
    private var helper = Helpers()
    private val db by lazy { DBHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        binding = initDataBinding()

        initDataBinding()

        initGameDetails()

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgFavorite.setOnClickListener {
            addRemoveFavorites()
        }

    }

    private fun initDataBinding() : ActivityGameDetailBinding{
        //binding = DataBindingUtil.inflate(layoutInflater , R.layout.activity_game_detail , findViewById(android.R.id.content) , false)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_game_detail)
        viewModel = ViewModelProvider(this).get(GameDetailActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding
    }

    fun initGameDetails(){
        getVideoGameDetail(StaticVariables.videoGame!!)
    }

    fun getVideoGameDetail(game : VideoGame){
        viewModel.isLoading.value = true
        viewModel.getVideoGameDetail(game.id.toString()).observe(this , Observer<VideoGameDetail>{ videoGameDetail ->
            viewModel.isLoading.value = false
            if(videoGameDetail != null){
                videoGame = videoGameDetail
                binding.videoGame = videoGameDetail

                prepareViewPager(prepareViewPagerImages(game.short_screenshots))
                prepareRatingBars(videoGame.ratings)

                showViews()
            }
        })
        initStar()
    }

    fun prepareViewPager(imageList : ArrayList<String>){

        //IMAGE SLIDER
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        if(!imageList.isNullOrEmpty()){
            for (i in 1..imageList.size - 1){
                viewPagerAdapter.addFrag(GeneralViewPager(imageList[i]),i.toString())
            }
        }

        viewpagerGallery.adapter = viewPagerAdapter

        spring_dots_indicator.setViewPager(viewpagerGallery)
    }

    fun prepareViewPagerImages(imageList : ArrayList<ScreenshotModel>) : ArrayList<String> {
        var iList = ArrayList<String>()
        for (i in imageList){
            iList.add(i.image)
        }
        return iList
    }

    fun prepareRatingBars(ratings : ArrayList<RatingModel> = ArrayList()){

        for (i in ratings){
            //Horizontal parent
            var linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.setPadding(0,16,0,0)
            linearLayout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

            var textView = TextView(this)
            var typeFace = ResourcesCompat.getFont(this , R.font.montserrat)
            textView.typeface = typeFace
            textView.setText(i.title)
            textView.maxLines = 1
            textView.ellipsize = TextUtils.TruncateAt.END
            textView.setTextColor(ContextCompat.getColor(this , R.color.colorWhite))
            textView.gravity = Gravity.CENTER_VERTICAL
            textView.setPadding(0,0,8,0)
            textView.layoutParams = LayoutParams(helper.intToDp(this , 120),LayoutParams.WRAP_CONTENT)

            var ratingbar = ProgressBar(this , null , android.R.attr.progressBarStyleHorizontal)
            ratingbar.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, helper.intToDp(this , 20))
            ratingbar.progressDrawable = ContextCompat.getDrawable(this , R.drawable.custom_rating_bar)
            ratingbar.progress = i.precent.toInt()

            linearLayout.addView(textView)
            linearLayout.addView(ratingbar)

            //Gerçek view
            linearLayoutRatings.addView(linearLayout)
        }

    }

    fun initStar(){
        var list = db.readData()
        for (i in list){
            if (StaticVariables.videoGame!!.id == i.id){
                //db de vardır star dolu gelsin
                imgFavorite.setImageResource(R.drawable.ic_filled_star)
            }
        }
    }

    fun addRemoveFavorites(){
        if(imgFavorite.drawable.constantState == ContextCompat.getDrawable(this , R.drawable.ic_filled_star)?.constantState) {
            //delete
            imgFavorite.setImageResource(R.drawable.ic_empty_star)
            db.deleteData(StaticVariables.videoGame!!)
        }
        else{
            //add
            imgFavorite.setImageResource(R.drawable.ic_filled_star)
            db.insertData(StaticVariables.videoGame!!)
        }
    }

    fun showViews(){
        binding.imgFavorite.visibility = View.VISIBLE
        binding.cnsGameBackground.visibility = View.VISIBLE
        binding.txtGameName.visibility = View.VISIBLE
        binding.txtGameReleaseDate.visibility = View.VISIBLE
        binding.txtGameRating.visibility = View.VISIBLE
        binding.txtGameDescription.visibility = View.VISIBLE
        binding.cnsGallery.visibility = View.VISIBLE
        binding.viewPagerConstraintLayout.visibility = View.VISIBLE
    }
}