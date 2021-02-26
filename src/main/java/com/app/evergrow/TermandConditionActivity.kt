package com.app.evergrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_termand_condition.*

class TermandConditionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termand_condition)
        agree_btn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}