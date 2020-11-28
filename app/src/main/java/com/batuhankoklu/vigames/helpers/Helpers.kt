package com.batuhankoklu.cotrack.Helpers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batuhankoklu.vigames.R
import com.batuhankoklu.vigames.activities.MasterActivity
import com.batuhankoklu.vigames.helpers.StaticVariables
import com.batuhankoklu.vigames.model.VideoGame
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_master.*

class Helpers {

    fun redirectFromTo(context : Context, to : Class<*>){
        var intent = Intent(context,to)
        context.startActivity(intent)

    }

    fun redirectAndClearAllPreviousActivities(context: Context, to : Class<*>){
        var intent = Intent(context,to)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    fun saveVideoGameToSharedPref(context: Context, videoGame: VideoGame){
        var sharedPreferences : SharedPreferences = context.getSharedPreferences("SharedPrefVideoGame",Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences.edit()
        var gson = Gson()
        var videoGame = gson.toJson(videoGame)
        editor.putString("videoGame",videoGame)
        editor.commit()
    }

    fun readVideoGameFromSharedPrefs(context : Context,sharedPrefName :String , sharedPrefKey : Int ) : VideoGame? {
        var sharedPreferences : SharedPreferences? = context.getSharedPreferences(sharedPrefName,sharedPrefKey)
        if(sharedPreferences != null){
            var editor : SharedPreferences.Editor = sharedPreferences!!.edit()
            var gson = Gson()
            var userJsonModel : String? = sharedPreferences?.getString("videoGame","")
            var userModel  = gson.fromJson(userJsonModel, VideoGame::class.java)
            return userModel
        }

        return null

    }

    fun changeFragment(activity: FragmentActivity , fragment: Fragment, fragmentTag : String) {
        val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    fun changePage(pageSelection : Int , bottomNavigationView : BottomNavigationView){
        var menu : Menu = bottomNavigationView.menu
        var menuItem : MenuItem = menu.getItem(pageSelection)
        menuItem.setChecked(true)

    }

    fun intToDp(context: Context , value : Int) : Int{
        return (value * context!!.resources.displayMetrics.density).toInt()
    }

    fun urlToImage(url: String?, imageView: ImageView , defaulImage : Drawable? , context: Context? , type : Int = -1) {

        try {
            Picasso.get()
                .load(url)
                //.placeholder(R.mipmap.img_icon_profile)
                //.error(R.mipmap.img_icon_profile)
                .into(imageView)


        }
        catch (ex : java.lang.Exception){
            if(type == 0){
                imageView.layoutParams.height = intToDp(context!!, 32)
                imageView.layoutParams.width  = intToDp(context!!, 32)
                imageView.requestLayout()
            }

            imageView.setImageDrawable(defaulImage)

        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


}