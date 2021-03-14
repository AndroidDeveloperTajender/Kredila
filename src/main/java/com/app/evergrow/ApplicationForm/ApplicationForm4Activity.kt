package com.app.evergrow.ApplicationForm

import android.app.*
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.KeyEvent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.CompoundButtonCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.app.evergrow.Common.Common
import com.app.evergrow.DashBoardActivity
import com.app.evergrow.R
import com.braintreepayments.cardform.view.CardForm
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.StripeIntent
import kotlinx.android.synthetic.main.activity_application_form4.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.time.Instant
import java.util.*
import kotlin.random.Random

class ApplicationForm4Activity : AppCompatActivity(), PaymentResultListener {

    lateinit var notificationManager: NotificationManager
    lateinit var notificaitonChannel: NotificationChannel
    lateinit var builder:Notification.Builder
    val channelId="com.earlypay.ApplicationForm"
    var db = FirebaseFirestore.getInstance()
    var dialogpayment:Dialog?=null
    var countryname:String= ""
      private lateinit var stripe: Stripe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_form4)
        dialogpayment =  Dialog(this)

        notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        laf_chk.text="Loan Agreement For("+ Common.form1list.get("Loan_amount").toString()+"rs)"
        countryname=Common.form1list.get("country").toString()



        nextFour_btn.setOnClickListener {
            if (sva_chk.isChecked&& laf_chk.isChecked&&sp_chk.isChecked){
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("EVERGROW")
                progressDialog.setCancelable(false)
                progressDialog.setMessage("Generating Your Agreements Please Wait..")
                progressDialog.show()

                Handler().postDelayed(
                    {
                        progressDialog.dismiss()
                        //   root. progressbar_a4.visibility=View.GONE
                        DialogOpen()
                    }, 15000L
                )



            }else{
                val colorStateList = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_checked),
                        intArrayOf(android.R.attr.state_checked)
                    ), intArrayOf(
                        Color.parseColor("#43306D"),  //unchecked color
                        Color.parseColor("#43306D")
                    )
                )

                CompoundButtonCompat.setButtonTintList(sp_chk, colorStateList)
                CompoundButtonCompat.setButtonTintList(laf_chk, colorStateList)
                CompoundButtonCompat.setButtonTintList(sva_chk, colorStateList)
                Toast.makeText(
                    applicationContext,
                    "Please All Aggrement approved",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        usernames4_tv.text="Dear "+capitizeString(
            Common.form1list.get("Name as per bank").toString()
        )+" Congratulations"

        stripe = Stripe(
            applicationContext,
            //"pk_test_51HjfqyH3I9CzC9sdB1lO4WWveKqpgyefMs4rgjKPORREFRnKwEGdWAC22psgAL9SNiFryX410vD1BGRLkPCocvyQ006JHqAeys"
            "pk_live_51HjfqyH3I9CzC9sd1Jrhjnazc2U65V9ZRtwRbQtzAxSWEdATGQiTvbxVAmygTth3VUwmZ7FtmCEKD1I8Ccne6Jk900GDBHtUkc"
        )

        Log.e("CHECKADD", Common.form1list.get("Address").toString())
          // startCheckout()
            //StartCheckOut()

        //IN_APP_PURCHASE()
    }

    private fun capitizeString(name: String): String {
        var captilizedString = ""
        if (name.trim { it <= ' ' } != "") {
            captilizedString = name.substring(0, 1).toUpperCase() + name.substring(1)
        }
        return captilizedString
    }

    private fun DialogOpen() {
        val dialog =  Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_layout)
     //   val name = dialog!!.findViewById(R.id.dialognameuser_tv) as TextView
    //    val dialogdescription_tv=dialog!!.findViewById<TextView>(R.id.dialogdescription_tv)

      //  name.text = "Dear ${capitizeString(Common.form1list.get("Name as per bank").toString())}"
      //  val yesBtn = dialog!!.findViewById(R.id.btn_apply) as Button
     //   var content="Your Loan Aggrements/Stamp Papers &amp; Sign Verification Aggrements Will Generate After Stamp Papers<font color='#3700B3'> Fees(Stamp Papers and Stamp duty fees Is Only paid By Applicants When is 399rs</font>  For Stamps Papers)\n EverGrow Has<font color='#3700B3'> (0% Application. 0% Process Fee. 0% Brokerage Fees).</font> <font color='#BB86FC'>499rs Is 100% Refundable.</font>With your Loan Instantly So don't Worry About your Loan and Fee."
        //dialogdescription_tv.setText(Html.fromHtml(content))
        //yesBtn.setOnClickListener {
      //      dialog.dismiss()
     //       startPayment()
    //    }
        dialog.show()

    }
    private fun startPayment() {

        val activity:Activity = this
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

        StoreFormDeatils()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
              super.onActivityResult(requestCode, resultCode, data)
              val weakActivity = WeakReference<Activity>(this)

          }

    fun ApplicationDialog(){
        db.collection("ApplicationForm").document().set(Common.form1list)
            .addOnSuccessListener {
                val dialog =Dialog(this)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setContentView(R.layout.dialogfinal_layout)
                val name = dialog!!.findViewById(R.id.usernamefinal_db) as TextView
                val applicationno = dialog!!.findViewById(R.id.dialogapno_tv) as TextView
                name.text = "Dear ${Common.form1list.get("Name as per bank")}"
                applicationno.text=Common.form1list.get("Applicationno")
                val yesBtn = dialog!!.findViewById(R.id.dialogfinish_btn) as Button

                applicationno!!.setOnClickListener {
                    val cm = this!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cm.text = applicationno!!.text
                    Toast.makeText(this, "Copy", Toast.LENGTH_LONG).show()

                }
                yesBtn.setOnClickListener {
                    dialog.dismiss()
                    // Common.bp.purchase(contex,"premiumproducts")
                    startActivity(Intent(applicationContext, DashBoardActivity::class.java))
                    // loanform.IN_APP_PURCHASE_EVENT()
                }

                dialog.show()

            }.addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Application Not Applied Please Try Again..",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun StoreFormDeatils(){
        db.collection("ApplicationForm").document().set(Common.form1list)
            .addOnSuccessListener {
                val dialog =Dialog(this)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setContentView(R.layout.dialogfinal_layout)
                val name = dialog!!.findViewById(R.id.usernamefinal_db) as TextView
                val applicationno = dialog!!.findViewById(R.id.dialogapno_tv) as TextView
                name.text = "Dear ${Common.form1list.get("Name as per bank")}"
                applicationno.text=Common.form1list.get("Applicationno")
                val yesBtn = dialog!!.findViewById(R.id.dialogfinish_btn) as Button

                yesBtn.setOnClickListener {
                    dialog.dismiss()
                    // Common.bp.purchase(contex,"premiumproducts")
                   // startActivity(Intent(applicationContext, DashBoardActivity::class.java))
                    ApplicationDialog()
                    sendNotification(
                        "EverGrow \\nPayment Successfully.\", \"Your Transaction has been Completed,Your Application Number is${
                            Common.form1list.get(
                                "Loan_amount"
                            )
                        }"
                    )
                    // loanform.IN_APP_PURCHASE_EVENT()
                }
                dialog.show()

            }.addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Application Not Applied Please Try Again..",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
            .setMessage("Dont Worry\n Your Payment Will Refund 100% in your account in both cases loan approve & Decline So Dont Worry Please Continue with your Application")
            .setPositiveButton("Apply & Get Loan") { arg0, arg1 ->

                if (sva_chk.isChecked&& laf_chk.isChecked&&sp_chk.isChecked) {
                    arg0.dismiss()
                    startPayment()

                }else{
                    Toast.makeText(
                        applicationContext,
                        "Please All Aggrement approved",
                        Toast.LENGTH_LONG
                    ).show()
                }
                // do something when the button is clicked
                //startActivity(Intent(this, DashBoardActivity::class.java))
                //close();
            }

            .show()
    }


    override fun onBackPressed() {
        moveTaskToBack(true)
       // finish()
        //onStop()
        super.onBackPressed()

    }

    private fun sendNotification(messageBody: String) {

        var intent=Intent(applicationContext, ApplicationForm4Activity::class.java)
        var pendingintent:PendingIntent=PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            notificaitonChannel=NotificationChannel(
                channelId,
                messageBody,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificaitonChannel.lightColor=Color.RED
            notificaitonChannel.enableVibration(true)
            notificaitonChannel.enableLights(true)
            notificationManager.createNotificationChannel(notificaitonChannel)

            builder=Notification.Builder(this, channelId)
                .setContentTitle("EverGrow")
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.fblogo)
                .setContentIntent(pendingintent)

        }else{
            builder=Notification.Builder(this)
                .setContentTitle("EverGrow")
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.earlypaylogo)
                .setContentIntent(pendingintent)
        }
        notificationManager.notify(0, builder.build())

    }

}


