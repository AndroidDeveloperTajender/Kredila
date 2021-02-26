package com.app.evergrow.ApplicationForm

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.app.evergrow.Common.Common
import com.app.evergrow.DashBoardActivity
import com.app.evergrow.R
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_application_form3.*
import org.json.JSONObject


import java.time.Instant
import kotlin.random.Random

class ApplicationForm3Activity : AppCompatActivity(), PaymentResultListener {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_form3)

        /*
      nextthree_btn.setOnClickListener {
            if (fullnamebank_et.text.toString().isEmpty()){
                fullnamebank_et.error="Please Enter Full Name As Per Bank"
            }
            else if (bankname_et.text.toString().isEmpty()){
               bankname_et.error="Please Enter Bank Name"
            }else if (accountno_et.text.toString().isEmpty()){
                accountno_et.error="Please Enter Account No"
            }
            else if (bankuid_et.text.toString().isEmpty()){
                bankuid_et.error="Please Enter Bank Code"
            }else if (blocation_et.text.toString().isEmpty()){
                blocation_et.error="Please Enter Branch Location"

            }else{

                val min = 10000000 //min and max values act as your 6 digit range

                val max = 99999900
                val randomizer = Random

                var applicationnumber =
                    min + randomizer.nextInt(max - min) + Instant.now().getEpochSecond();
                Log.e("Applicationno",applicationnumber.toString())
                Common.form1list.put("Name as per bank",fullnamebank_et.text.toString())
                Common.form1list.put("Bank name",bankname_et.text.toString())
                Common.form1list.put("Account number",accountno_et.text.toString())
                Common.form1list.put("Bank unique code",bankuid_et.text.toString())
                Common.form1list.put("branch location",fullnamebank_et.text.toString())
                Common.form1list.put("Applicationno",applicationnumber.toString())
                Common.form1list.put("uid", FirebaseAuth.getInstance().uid.toString())
                Common.form1list.put("uid", FirebaseAuth.getInstance().uid.toString())

                startActivity(Intent(this,ApplicationForm4Activity::class.java))

            }
        }
         */
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            exitByBackKey()

            //moveTaskToBack(false);
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    protected fun exitByBackKey() {
        val alertbox = androidx.appcompat.app.AlertDialog.Builder(this)
            .setMessage("This Application will help for your financial need Instantly")
            .setPositiveButton("Continue is HelpFul") { arg0, arg1 ->
                arg0.dismiss()
                // do something when the button is clicked

                //close();
            }

            .show()
    }


    override fun onBackPressed() {
        moveTaskToBack(true)
        super.onBackPressed()
    }

    private fun startPayment() {

        val activity: Activity = this
        val checkout = Checkout()
        checkout.setKeyID("rzp_live_EznTsNsD2zCTnL")
        checkout.setImage(R.drawable.fblogo)


        try {
            val options = JSONObject()
            options.put("name", "EverGrow")
            options.put("description", "Finance Service")
            //You can omit the image option to fetch the image from dashboard
            //    options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            //    options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            //options.put("order_id", "Order_t"+tid);
            options.put("amount", 399*100)//pass amount in currency subunits
            val prefill = JSONObject()
            prefill.put("email", Common.form1list.get("emailid"))
            prefill.put("contact", Common.form1list.get("phonenumner"))
            options.put("prefill", prefill)
            checkout.open(activity, options)
        }catch (e: Exception){
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
    override fun onPaymentError(errorCode: Int, response: String?) {

    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {

        //StoreFormDeatils()
    }
}