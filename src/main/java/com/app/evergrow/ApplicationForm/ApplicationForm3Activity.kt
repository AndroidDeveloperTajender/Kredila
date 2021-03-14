package com.app.evergrow.ApplicationForm

import android.app.*
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.get
import com.app.evergrow.Common.Common
import com.app.evergrow.DashBoardActivity
import com.app.evergrow.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_application_form2.*
import kotlinx.android.synthetic.main.activity_application_form3.*
import org.json.JSONObject


import java.time.Instant
import kotlin.random.Random

class ApplicationForm3Activity : AppCompatActivity(), PaymentResultListener {

    var assetsdetails=""
    var cprofession=""
    var seekbar_leadselects=""
    var statelocation=""

    lateinit var notificationManager: NotificationManager
    lateinit var notificaitonChannel: NotificationChannel
    lateinit var builder:Notification.Builder
    var db = FirebaseFirestore.getInstance()
    val channelId="com.app.evergrow.ApplicationForm"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_form3)
        val state = resources.getStringArray(R.array.state)
        val stateadapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, state)
        locationstate_sp.adapter = stateadapter
        locationstate_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                statelocation = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val profession = resources.getStringArray(R.array.profession)
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, profession)
        customerprofession_sp.adapter = adapter
        // income_sp
        customerprofession_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                cprofession = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        seekbar_assestdetail.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, fromUser: Boolean) {
                assetsdetails=p1.toString()
                assetstitle_tv.text = "Assets Details:     ${p1}"
            }

            override fun onStartTrackingTouch(p1: SeekBar?) {
              //  assetsdetails=p1.toString()
              //  assetstitle_tv.text = "Assets Details:     ${assetsdetails}"
            }

            override fun onStopTrackingTouch(p1: SeekBar?) {
             //   assetsdetails=p1.toString()
             //   assetstitle_tv.text = "Assets Details:     ${assetsdetails}"
            }

        })
        seekbar_leadselect.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, fromUser: Boolean) {
                seekbar_leadselects=p1.toString()
                   leadselect_tv.text = "Customer Age Required:     ${seekbar_leadselects}"
            }

            override fun onStartTrackingTouch(p1: SeekBar?) {
             //   seekbar_leadselects=p1.toString()
              //    leadselect_tv.text = "Customer Age Required:     ${seekbar_leadselects}"
            }

            override fun onStopTrackingTouch(p1: SeekBar?) {
            //    seekbar_leadselects=p1.toString()
             //     leadselect_tv.text = "Customer Age Required:     ${seekbar_leadselects}"
            }

        })

      nextthree_btn.setOnClickListener {
            if (statelocation.toString().isEmpty()){
               // statelocation.error="Please Enter Full Name As Per Bank"
                Toasty.warning(applicationContext,"Please Choose State ${statelocation}",Toasty.LENGTH_LONG).show()
            }
            else if (cprofession.toString().isEmpty()){
                Toasty.warning(applicationContext,"Please Choose Customer Profession",Toasty.LENGTH_LONG).show()

            }else if (assetsdetails.toString().isEmpty()){
                Toasty.warning(applicationContext,"Please Select Assets Details",Toasty.LENGTH_LONG).show()

            }
            else if (seekbar_leadselects.toString().isEmpty()){
                Toasty.warning(applicationContext,"Please Select Lead",Toasty.LENGTH_LONG).show()

            }else{

                val min = 10000000 //min and max values act as your 6 digit range

                val max = 99999900
                val randomizer = Random

                var applicationnumber =
                    min + randomizer.nextInt(max - min) + Instant.now().getEpochSecond();
                Log.e("Applicationno",applicationnumber.toString())
                Common.form1list.put("Location",statelocation)
                Common.form1list.put("CProfession",cprofession)
                Common.form1list.put("Assets_details",assetsdetails)
                Common.form1list.put("Lead_select",seekbar_leadselects)
                Common.form1list.put("Applicationno",applicationnumber.toString())
                Common.form1list.put("uid", FirebaseAuth.getInstance().uid.toString())
                OpenDialogTerm()
              //  startActivity(Intent(this,ApplicationForm4Activity::class.java))
            }
        }
    }

    private fun OpenDialogTerm() {
        val dialog =  Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_layout)
        val name = dialog!!.findViewById(R.id.dialognameuser_tv) as TextView
       // val dialogdescription_tv=dialog!!.findViewById<TextView>(R.id.dialogdescription_tv)
      //  name.text = "Dear ${capitizeString(Common.form1list.get("Name as per bank").toString())}"
        val submitdialog_btn = dialog!!.findViewById(R.id.submitdialog_btn) as Button
      //var content="Your Loan Aggrements/Stamp Papers &amp; Sign Verification Aggrements Will Generate After Stamp Papers<font color='#3700B3'> Fees(Stamp Papers and Stamp duty fees Is Only paid By Applicants When is 399rs</font>  For Stamps Papers)\n EverGrow Has<font color='#3700B3'> (0% Application. 0% Process Fee. 0% Brokerage Fees).</font> <font color='#BB86FC'>499rs Is 100% Refundable.</font>With your Loan Instantly So don't Worry About your Loan and Fee."
     // dialogdescription_tv.setText(Html.fromHtml(content))
        name.text= Common.form1list.get("fullname")
        submitdialog_btn.setOnClickListener {
            dialog.dismiss()
           // startPayment()
            OpenDialogTerm2()
        }
        dialog.show()
    }
    private fun OpenDialogTerm2() {
        val dialog =  Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_layoutsecond)
        val name = dialog!!.findViewById(R.id.dialognameusersecond_tv) as TextView
        // val dialogdescription_tv=dialog!!.findViewById<TextView>(R.id.dialogdescription_tv)
        //  name.text = "Dear ${capitizeString(Common.form1list.get("Name as per bank").toString())}"
        val submitdialog_btn = dialog!!.findViewById(R.id.submitdialogsecond_btn) as Button
        //var content="Your Loan Aggrements/Stamp Papers &amp; Sign Verification Aggrements Will Generate After Stamp Papers<font color='#3700B3'> Fees(Stamp Papers and Stamp duty fees Is Only paid By Applicants When is 399rs</font>  For Stamps Papers)\n EverGrow Has<font color='#3700B3'> (0% Application. 0% Process Fee. 0% Brokerage Fees).</font> <font color='#BB86FC'>499rs Is 100% Refundable.</font>With your Loan Instantly So don't Worry About your Loan and Fee."
        // dialogdescription_tv.setText(Html.fromHtml(content))
        name.text="Dear ${Common.form1list.get("fullname")}"
        submitdialog_btn.setOnClickListener {
            dialog.dismiss()
            startPayment()
        }
        dialog.show()
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
        checkout.setKeyID("rzp_test_haWT7PbovfBhMa")
        checkout.setImage(R.drawable.fblogo)


        try {
            val options = JSONObject()
            options.put("name", "LeadGram")
            options.put("description", "Lead Generationx")
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

    fun StoreFormDeatils(){
        db.collection("ApplicationForm").document().set(Common.form1list)
                .addOnSuccessListener {
                    val dialog =Dialog(this)
                    dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(false)
                   // dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.setContentView(R.layout.dialogfinal_layout)
                    val name = dialog!!.findViewById(R.id.usernamefinal_db) as TextView
                    val applicationno = dialog!!.findViewById(R.id.dialogapno_tv) as TextView
                    name.text = "Dear ${Common.form1list.get("fullname")}"
                    applicationno.text=Common.form1list.get("Applicationno")
                    val yesBtn = dialog!!.findViewById(R.id.dialogfinish_btn) as Button

                    yesBtn.setOnClickListener {
                        dialog.dismiss()
                        // Common.bp.purchase(contex,"premiumproducts")
                         startActivity(Intent(applicationContext, DashBoardActivity::class.java))
                        //ApplicationDialog()
                      /*  sendNotification(
                                "EverGrow \\nPayment Successfully.\", \"Your Transaction has been Completed,Your Application Number is${
                                    Common.form1list.get(
                                            "Loan_amount"
                                    )
                                }")*/
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

    fun ApplicationDialog(){
        db.collection("ApplicationForm").document().set(Common.form1list)
                .addOnSuccessListener {
                    val dialog =Dialog(this)
                    dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(false)
                  //  dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
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

    private fun sendNotification(messageBody: String) {

        var intent=Intent(applicationContext, ApplicationForm4Activity::class.java)
        var pendingintent: PendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            notificaitonChannel= NotificationChannel(
                    channelId,
                    messageBody,
                    NotificationManager.IMPORTANCE_HIGH
            )
            notificaitonChannel.lightColor= Color.RED
            notificaitonChannel.enableVibration(true)
            notificaitonChannel.enableLights(true)
            notificationManager.createNotificationChannel(notificaitonChannel)

            builder= Notification.Builder(this, channelId)
                    .setContentTitle("EverGrow")
                    .setContentText(messageBody)
                    .setSmallIcon(R.drawable.fblogo)
                    .setContentIntent(pendingintent)

        }else{
            builder= Notification.Builder(this)
                    .setContentTitle("EverGrow")
                    .setContentText(messageBody)
                    .setSmallIcon(R.drawable.earlypaylogo)
                    .setContentIntent(pendingintent)
        }
        notificationManager.notify(0, builder.build())

    }
}