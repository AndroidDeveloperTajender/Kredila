package com.app.evergrow

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.baoyachi.stepview.VerticalStepView
import kotlinx.android.synthetic.main.activity_tracked.*

class TrackedActivity : AppCompatActivity() {


    lateinit var mSetpview0: VerticalStepView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracked)



        var animations=AnimationUtils.loadAnimation(this,R.anim.fade_in)
            //    toolbar_track.set
        val list0: MutableList<String> = ArrayList()
        list0.add("Application Form Submitted")
        list0.add("Document Submitted")
        list0.add("Application Under Review\n(It May Take 3-15 Working Days)")
        list0.add("Approved\n(It May Take 3-15 working Days)")

        list0.add("Total Loan Transaction")
        list0.add("Distributed")



     //   step_view0.startAnimation(animations)


           step_view0.setStepsViewIndicatorComplectingPosition(list0.size - 4)
            .reverseDraw(false)//default is true
            .setStepViewTexts(list0)//总步骤
            .setLinePaddingProportion(0.85f)

            .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(applicationContext, android.R.color.black))
            .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(applicationContext, android.R.color.black))
            .setStepViewComplectedTextColor(ContextCompat.getColor(applicationContext, R.color.colorViolate))
            .setStepViewUnComplectedTextColor(ContextCompat.getColor(applicationContext, R.color.colorViolate))
               .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(applicationContext, R.mipmap.ic_complete))
               .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(applicationContext, R.mipmap.ic_on))
               .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(applicationContext, R.mipmap.ic_eye))

        Handler().postDelayed({
            step_view0.setStepsViewIndicatorComplectingPosition(list0.size - 3)
                .reverseDraw(false)//default is true
                .setStepViewTexts(list0)//总步骤
                .setLinePaddingProportion(0.85f)
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(applicationContext, android.R.color.black))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(applicationContext, android.R.color.black))
                .setStepViewComplectedTextColor(ContextCompat.getColor(applicationContext, R.color.colorViolate))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(applicationContext, R.color.colorViolate))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(applicationContext, R.mipmap.ic_complete))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(applicationContext, R.mipmap.ic_on))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(applicationContext, R.mipmap.ic_eye))

        },2000)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,DashBoardActivity::class.java))
    }
}