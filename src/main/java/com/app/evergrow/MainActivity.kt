
package com.app.evergrow

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.evergrow.Adapter.ScreenSlidePagerAdapter
import com.app.evergrow.Fragment.OnBoradingIntro1
import com.app.evergrow.Fragment.OnBoradingIntro2

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var pagerAdapter: ScreenSlidePagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null){
            sbg.animate().translationY(-3200f).setDuration(1000).setStartDelay(4000)
            //logo.animate().translationY(1600f).setDuration(1000).startDelay = 4000
         //   Appname.animate().translationY(1800f).setDuration(1000).setStartDelay(4000)
        //    lottie.animate().translationY(1600f).setDuration(1000).setStartDelay(4000)

            Handler().postDelayed({
                val intent = Intent(applicationContext, DashBoardActivity::class.java)
                startActivity(intent)
            },4000);

        }else{
            sbg.animate().translationY(-3200f).setDuration(1000).setStartDelay(4000)
           // logo.animate().translationY(1600f).setDuration(1000).startDelay = 4000
          //  Appname.animate().translationY(1800f).setDuration(1000).startDelay = 4000
         //   lottie.animate().translationY(1600f).setDuration(1000).setStartDelay(4000)

          //  var onboarding1:OnBoradingIntro1=OnBoradingIntro1.newInstance("a faf")
           // var onboarding2:OnBoradingIntro2=OnBoradingIntro2.newInstance("sadfs ")

            pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager,0)
            pagerAdapter!!.addFragment(OnBoradingIntro1(), "c")
            pagerAdapter!!.addFragment(OnBoradingIntro2(), "c")
           // pager.setAdapter(pagerAdapter)
        }
    }
}