package com.app.evergrow.ApplicationForm

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.*
import com.app.evergrow.Common.Common
import com.app.evergrow.DashBoardActivity
import com.app.evergrow.R
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_application_form1.*
import kotlinx.android.synthetic.main.activity_application_form2.*

import java.io.File
import java.io.IOException

class ApplicationForm2Activity : AppCompatActivity() {
    var imageselced = false
    var imageselced1=false
    var imageselced2=false
    var imageselced3=false


    var Profession=""
   var applicantdetails=""
    var hometype=""
    var urione: Uri?=null
    var uritwo: Uri?=null
    var urithree: Uri?=null
    var urifour: Uri?=null
    var loanAmount: String="50"
    var cibil:String="500"
    var hour: String="24hr"
    var applicantage="20"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_form2)


       // amount_choose_tv.max=1000

      seekbar_amount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {


                loanAmount=p1.toString()
                amount_choose_tv.text = "How Much Amount Needed          ${loanAmount}rs"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                loanAmount= p0!!.progress.toString()
                amount_choose_tv.text = "How Much Amount Needed          ${loanAmount}rs"
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                loanAmount= p0!!.progress.toString()
                amount_choose_tv.text = "How Much Amount Needed          ${loanAmount}rs"
            }

        })

        val languages = resources.getStringArray(R.array.Languages)
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
        income_sp.adapter = adapter
       // income_sp
        income_sp.onItemSelectedListener=object:AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
      seekbar_hr.max=72
        seekbar_hr.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                hour=p1.toString()
                hr_choose_tv.text = "Choose Tenure:         ${hour}hr"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

                hour= p0!!.progress.toString()

                hr_choose_tv.text = "Choose Tenure:         ${hour}M"
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                hour= p0!!.progress.toString()

                hr_choose_tv.text = "Choose Tenure:         ${hour}M"
            }
        })
        seekbar_cibil.max=900
        seekbar_cibil.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                cibil=p1.toString()
                cibil_choose_tv.text = "Choose Tenure:         ${cibil}hr"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

                hour= p0!!.progress.toString()

                cibil_choose_tv.text = "Choose Tenure:         ${cibil}M"
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                hour= p0!!.progress.toString()

                cibil_choose_tv.text = "Choose Tenure:         ${cibil}M"
            }
        })
        seekbar_cibil.max=900
        seekbar_cibil.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                applicantage=p1.toString()
                applicantage_choose_tv.text = "Applicant Age:         ${applicantage}hr"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

                hour= p0!!.progress.toString()

                applicantage_choose_tv.text = "Applicant Age:         ${applicantage}M"
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                applicantage= p0!!.progress.toString()

                applicantage_choose_tv.text = "Applicant Age:         ${applicantage}M"
            }
        })


        Profession_rg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->

            when (i) {
                R.id.salared_rb -> {
                    Profession = "Rent"
                 //   monthrent_et.visibility = View.VISIBLE
                }
                R.id.selfemployed_rb -> {
                    Profession = "Owned"
                  //  monthrent_et.visibility = View.GONE
                }
            }

        })
        applicantdetails_Check_rg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->

            when (i) {
                R.id.mandateory_rb -> {
                    applicantdetails = "Rent"
                    //  monthrent_et.visibility = View.VISIBLE
                }
                R.id.non_mandetory_rb -> {
                    applicantdetails = "Owned"
                    //monthrent_et.visibility = View.GONE
                }
            }

        })
        Resident_Check_rg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->

            when (i) {
                R.id.rented_rb -> {
                    hometype = "Rent"
                  //  monthrent_et.visibility = View.VISIBLE
                }
                R.id.owned_rb -> {
                    hometype = "Owned"
                    //monthrent_et.visibility = View.GONE
                }
            }

        })


        nexttwo_btn.setOnClickListener {
            Log.e("CHECKNAME", Common.form1list.get("fullname").toString())
            if (urione==null){
                 Toasty.warning(applicationContext,"Please PAN Select and Capture", Toast.LENGTH_LONG).show()
                Log.e("ERRORSF","one")
            }else if (uritwo==null){
               Toasty.warning(applicationContext,"Please Adhaar Front Select and Capture",
                    Toast.LENGTH_LONG).show()
                Log.e("ERRORSF","two")
            }else if (urithree==null){
                Toasty.warning(applicationContext,"Please Adhaar Back Select and Capture",
                    Toast.LENGTH_LONG).show()
                Log.e("ERRORSF","three")
            }else if (urifour==null){
              Toasty.warning(applicationContext,"Please Photo Select and Capture",
                    Toast.LENGTH_LONG).show()
                Log.e("ERRORSF","four")
            }else{

                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("EVERGROW")
                progressDialog.setMessage("Calculations is in Process Please Wait..")
                progressDialog.setCancelable(false)
                progressDialog.show()
                Handler().postDelayed(
                    {
                        progressDialog.dismiss()
                        // root. progressbar_a2.visibility=View.GONE
                       // openDialog()
                    },15000L)
            }
        }

    }

  /*  private fun openDialog() {
        val dialog =  Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
      //  dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog1_layout)
        val name = dialog!!.findViewById(R.id.username_db) as TextView
        val amountduratioin = dialog!!.findViewById(R.id.lamount_duration_tv) as TextView
        name.text = "Dear ${Common.form1list.get("Name as per bank")}"
        val yesBtn = dialog!!.findViewById(R.id.dilgobtn) as Button

        name.text="Dear ${   capitizeString(Common.form1list.get("fullname").toString())}"
        amountduratioin.text=" ${loanAmount}rs for ${loanMonth} Months."

        yesBtn.setOnClickListener {
            dialog.dismiss()

            Common.form1list.put("Loan_amount",loanAmount)
            Common.form1list.put("Loan_Month",loanMonth)

            startActivity(Intent(this,ApplicationForm3Activity::class.java))
        }

        dialog.show()
    }*/

    private fun capitizeString(name: String): String {
        var captilizedString = ""
        if (name.trim { it <= ' ' } != "") {
            captilizedString = name.substring(0, 1).toUpperCase() + name.substring(1)
        }
        return captilizedString
    }

    fun FirstImage(){
        CropImage.activity().start(this);
        imageselced=true

        //  return urione.toString()
    }
    fun TwoImage(){
        CropImage.activity().start(this);
        imageselced1=true

        //  return urione.toString()
    }
    fun ThreeImage(){
        CropImage.activity().start(this);
        imageselced2=true

        //  return urione.toString()
    }
    fun FourImage(){
        CropImage.activity().start(this);
        imageselced3=true

        //  return urione.toString()
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

                // do something when the button is clicked
                arg0.dismiss()
                //close();
            }
            .show()
    }


    override fun onBackPressed() {

        moveTaskToBack(true)





        super.onBackPressed()

    }
}