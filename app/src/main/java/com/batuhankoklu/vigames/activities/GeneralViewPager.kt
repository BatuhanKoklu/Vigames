package com.batuhankoklu.vigames.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.batuhankoklu.cotrack.Helpers.Helpers
import com.batuhankoklu.vigames.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class GeneralViewPager(imageUrl : String?) : Fragment() {

    var helper = Helpers()
    var imageUrl = imageUrl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var v = inflater.inflate(R.layout.general_view_pager,container,false)

        var imgGeneralImage : ImageView = v.findViewById<ImageView>(R.id.imgGeneralImage)

        Picasso.get().load(imageUrl).noFade().fit().centerCrop().into(imgGeneralImage , object : Callback {
            override fun onSuccess() {
                imgGeneralImage.animate().setDuration(300).alpha(1f).start()
            }

            override fun onError(e: Exception?) {
                Log.e("tagy" , "image load fail")
            }

        })

        //helper.urlToImage(imageUrl , imgGeneralImage , null , requireContext())


        return v
    }



    companion object {

        fun newInstance() : GeneralViewPager = GeneralViewPager(null)
    }
}