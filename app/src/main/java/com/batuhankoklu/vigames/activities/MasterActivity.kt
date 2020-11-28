package com.batuhankoklu.vigames.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.batuhankoklu.cotrack.Helpers.Helpers
import com.batuhankoklu.vigames.R
import com.batuhankoklu.vigames.helpers.StaticVariables
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_master.*

class MasterActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener {

    var helper = Helpers()
    var lasSelectedItemId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)

        initBottomNavView()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //bottom navigationdan o sayfada ise reload olmasını engellendi
        if(item.itemId == lasSelectedItemId){
            return true
        }
        when (item.itemId) {
            R.id.btnHome -> {
                lasSelectedItemId = item.itemId
                helper.changeFragment(this , HomeFragment(), "HomeFragment")
                return true
            }
            R.id.btnFavorites -> {
                lasSelectedItemId = item.itemId
                helper.changeFragment(this , FavoritesFragment(), "FavoritesFragment")
                return true
            }

        }
        return false
    }

    fun initBottomNavView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        var pageSelection = StaticVariables.masterPageSelection


        if(pageSelection == 0){
            helper.changeFragment(this , HomeFragment(),"HomeFragment")
            helper.changePage(0 , bottomNavigationView)
        }
        else if(pageSelection == 1){
            helper.changeFragment(this , FavoritesFragment(),"FavoritesFragment")
            helper.changePage(1 , bottomNavigationView)
        }
        else{
            helper.changeFragment(this , HomeFragment(),"HomeFragment")
        }
    }


}

