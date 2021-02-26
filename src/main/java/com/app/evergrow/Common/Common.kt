package com.app.evergrow.Common

import android.app.Activity
import android.content.Context
import androidx.annotation.Nullable
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.app.evergrow.Model.Form1
import com.irozon.sneaker.Sneaker
import java.util.*
import kotlin.collections.HashMap

object Common{


     var KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT"
    var KEY_DOCUMENT="KEY_DOCUMENT"
     var KEY_DOCUMENT_STORE="KEY_DOCUMENT_STORE"
     var KEY_STEP = "STEP"
     var KEY_BARBER_LOAD_DONE = "BARBER_LOAD_DONE"
     fun WarningMsg(activity: Activity, title: String, message: String){
        Sneaker.with(activity) // Activity, Fragment or ViewGroup
            .setTitle(title)
            .setMessage(message)
            .sneakWarning()
    }
    fun getSharePreferenceString(key: String?, context: Context): String? {
        val sp = context.getSharedPreferences("EVENT", Context.MODE_PRIVATE)
        return sp.getString(key, null)
    }
    fun storeSharedPreference(key: String?, value: String?, context: Context) {
        val sp = context.getSharedPreferences("EVENT", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.commit()
    }
    var form1list=HashMap<String, String>()


}