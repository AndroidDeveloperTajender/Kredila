package com.app.evergrow.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.evergrow.LoginActivity
import com.app.evergrow.R
import kotlinx.android.synthetic.main.fragment_on_borading_intro2.view.*


class OnBoradingIntro2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_on_borading_intro2, container, false)
        root.OnBoarding2skip_tv.setOnClickListener {
            val intents = Intent(context, LoginActivity::class.java)
            intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intents)
        }

        root.registerintro_btn.setOnClickListener {
            val intents = Intent(context, LoginActivity::class.java)
            startActivity(intents)
        }
        return root
    }

}