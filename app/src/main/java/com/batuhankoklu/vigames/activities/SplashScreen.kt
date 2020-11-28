package com.batuhankoklu.vigames.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.batuhankoklu.cotrack.Helpers.Helpers
import com.batuhankoklu.vigames.R
import com.batuhankoklu.vigames.helpers.StaticVariables

class SplashScreen : AppCompatActivity() {

    var helper = Helpers()

    var mDelayHandler: Handler? = null
    val SPLASH_DELAY: Long = 3000 //3 seconds

    val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            helper.redirectAndClearAllPreviousActivities(this, MasterActivity::class.java)

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        if(StaticVariables.isEnteredBefore == 0){
            //Initialize the Handler
            mDelayHandler = Handler()

            //Navigate with delay
            mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

            StaticVariables.isEnteredBefore = StaticVariables.isEnteredBefore + 1

        }else{
            finish()
        }

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}