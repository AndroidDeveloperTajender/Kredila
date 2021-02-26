package com.app.evergrow.ApplicationForm


import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.app.evergrow.Common.Common
import com.app.evergrow.Common.Utils
import com.app.evergrow.R
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_application_form1.*
import java.util.*


class ApplicationForm1Activity : AppCompatActivity() , DatePickerDialog.OnDateSetListener{

    var worktypes:String="Salary"
    var hometype:String="Owned"
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0


    var country:String=""
    var city:String=""
    var state:String=""
    var postalCode:String=""
    var knownName:String=""
    var address:String=""


    var rented_rbs:RadioButton?=null
    var owned_rbs:RadioButton?=null

    var businesss_rb:RadioButton?=null
    var salarys_rb:RadioButton?=null
    var students_rb:RadioButton?=null
    var unemployeds_rb:RadioButton?=null
    lateinit var city_et:AutoCompleteTextView

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private var latitudeLabel: Double = 0.0
    private var longitudeLabel: Double = 0.0
    private var PERMISSION_ID=1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_form1)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


       // getLastLocation()
        city_et=findViewById<AutoCompleteTextView>(R.id.city_actv)
        salarys_rb=findViewById(R.id.salary_bg)
        businesss_rb=findViewById(R.id.business_bg)
        students_rb=findViewById(R.id.student_bg)
        unemployeds_rb=findViewById(R.id.noworking_bg)
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        rented_rbs=findViewById(R.id.rented_rb)
        owned_rbs=findViewById(R.id.owned_rb)
        Common.form1list.clear()
        //val languages = resources.getStringArray(R.array.COUNTRIES)
        val adapter= ArrayAdapter(this,
                android.R.layout.simple_list_item_1, Utils.COUNTRIES)

            city_et.setAdapter(adapter)
        hometype_rg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->

            when (i) {
                R.id.rented_rb -> {
                    hometype = "Rent"
                    monthrent_et.visibility = View.VISIBLE
                }
                R.id.owned_rb -> {
                    hometype = "Owned"
                    monthrent_et.visibility = View.GONE
                }
            }

        })
/*
        salarys_rb!!.setOnClickListener {
            if (salarys_rb!!.isChecked||salarys_rb!!.isSelected){
                worktypes="Salary"
                students_rb!!.isChecked=false
                unemployeds_rb!!.isChecked=false
                businesss_rb!!.isChecked=false
                cname_et.hint = "Company Name"
                cname_et.visibility = View.VISIBLE
                workexp_et.hint = "Total Work Experience"
                gsalary_et.hint = "Gross Salary"
            }
        }

        students_rb!!.setOnClickListener {
            if (students_rb!!.isChecked||students_rb!!.isSelected){
                worktypes="Student"
                salarys_rb!!.isChecked=false
                unemployeds_rb!!.isChecked=false
                businesss_rb!!.isChecked=false
                cname_et.hint = "Company Name"
                cname_et.setText("No")
                cname_et.visibility = View.GONE
                workexp_et.hint = "Brief Your Monthly Income Source"
                gsalary_et.hint = "Total Monthly Income"
            }
        }

        unemployeds_rb!!.setOnClickListener {
            if (unemployeds_rb!!.isChecked||unemployeds_rb!!.isSelected){
                worktypes="Un-Employed"
                students_rb!!.isChecked=false
                salarys_rb!!.isChecked=false
                businesss_rb!!.isChecked=false
                cname_et.hint = "Company Name"
                cname_et.setText("No")
                cname_et.visibility = View.GONE
                workexp_et.hint = "Brief Your Monthly Income Source"
                gsalary_et.hint = "Total Monthly Income"
            }
        }

        businesss_rb!!.setOnClickListener {
            if (businesss_rb!!.isChecked||businesss_rb!!.isSelected){
                worktypes="Business"
                students_rb!!.isChecked=false
                unemployeds_rb!!.isChecked=false
                salarys_rb!!.isChecked=false
                cname_et.setText("")
                cname_et.visibility = View.VISIBLE
                cname_et.hint = "Company Name"
                workexp_et.hint = "Company Work Experience"
                gsalary_et.hint = "Monthly Income"
            }
        }*/

        nextone_btn.setOnClickListener {

            Log.e("APPLICATIONFORM", "STEP 1")

           // if (owned_rbs!!.isChecked) {

                if (fullname_et.text.toString().isEmpty()) {
                    Log.e("APPLICATIONFORM2", "fullname Empty")
                    fullname_et.error = "Please Enter Full Name"
                } else if (mobilenumber_et.text.toString().isEmpty()) {
                   mobilenumber_et.error = "Please Enter Phone Number"
                } else if (emailid_et.text.toString().isEmpty()) {
                    emailid_et.error = "Please Enter Email- ID"
                }else if(city_actv.text.toString().isEmpty()){
                    city_actv.error="Please Enter City"
                }else if (designation_et.text.toString().isEmpty()){
                    designation_et.error="Please Enter Your Designation"
                } else if (cname_et.text.toString().isEmpty()) {
                    cname_et.error = "Please Enter Company Name"
                } else {

                    Common.form1list.put("fullname", fullname_et.text.toString())
                    Common.form1list.put("phonenumner", mobilenumber_et.text.toString())
                    Common.form1list.put("emailid", emailid_et.text.toString())
                    Common.form1list.put("city", city_actv.text.toString())
                    Common.form1list.put("company_name", cname_et.text.toString())
                    Common.form1list.put("pincode", pincode_et.text.toString())
                    Common.form1list.put("designation", designation_et.text.toString())

                    //  Common.form1list.put("Address", address + " \nEmailid:-" + emailid_et.text.toString() + " \n Phone No:" + mobilenumber_et.text.toString() )
                    startActivity(Intent(this, ApplicationForm2Activity::class.java))

                }
                /*   }
                   else if (rented_rbs!!.isChecked){
                       if (fullname_et.text.toString().isEmpty()) {
                           Log.e("APPLICATIONFORM2", "fullname Empty")
                           fullname_et.error = "Please Enter Full Name"
                       } else if (mobilenumber_et.text.toString().isEmpty()) {
                           mobilenumber_et.error = "Please Enter Phone Number"
                       } else if (emailid_et.text.toString().isEmpty()) {
                           emailid_et.error = "Please Enter Email- ID"
                       } else if (dob_tv.text.toString().isEmpty()) {
                           dob_tv.error = "Please Select DOB"
                       }else if (monthrent_et.text.toString().isEmpty()) {
                           monthrent_et.error = "Please Enter Your Monthly Rent"
                       }
                       else if (address_et.text.toString().isEmpty()) {
                          address_et.error = "Please Enter Full Address"
                       } else if (cname_et.text.toString().isEmpty()) {
                           cname_et.error = "Please Enter Company Name"
                       } else if (workexp_et.text.toString().isEmpty()) {
                           workexp_et.error = "Please Enter Your Total Work Experience"
                       }else if (gsalary_et.text.toString().isEmpty()) {
                          gsalary_et.error = "Please Enter Your Gross Salary"
                       }  else {
                           Common.form1list.put("fullname", fullname_et.text.toString())
                           Common.form1list.put("phonenumner", mobilenumber_et.text.toString())
                           Common.form1list.put("emailid", emailid_et.text.toString())
                           Common.form1list.put("dob", dob_tv.text.toString())
                           Common.form1list.put("monthly_rent", monthrent_et.text.toString())
                           Common.form1list.put("address", address_et.text.toString())
                           Common.form1list.put("company_name", cname_et.text.toString())
                           Common.form1list.put("work_expirence", workexp_et.text.toString())
                           Common.form1list.put("gross_salary or income", gsalary_et.text.toString())
                           Common.form1list.put("residental", hometype)
                           Common.form1list .put("work_type", worktypes)
                           Common.form1list.put("city", city)
                           Common.form1list.put("state", state)
                           Common.form1list.put("postalcode",postalCode)
                           Common.form1list.put("country",country)
                           Common.form1list.put("Address",address + " \nEmailid:-" + emailid_et.text.toString() + " \n Phone No:" + mobilenumber_et.text.toString())

                           startActivity(Intent(this, ApplicationForm2Activity::class.java))
                       }
                   }*/

        }

    }

    private val locationCallback=object: LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            var lastLocation=p0!!.lastLocation
            latitudeLabel=lastLocation.latitude
            longitudeLabel=lastLocation.longitude
        }
    }

    fun CheckPermission():Boolean{
        if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                )== PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)==
            PackageManager.PERMISSION_GRANTED ){
            return true
        }

        return false
    }

    fun RequestPermission(){
        ActivityCompat.requestPermissions(
                this,
                arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ), PERMISSION_ID
        )
    }

    fun isLocationEnabled():Boolean{
        var locationManager: LocationManager =getSystemService(Context.LOCATION_SERVICE)as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )


    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {

        if (requestCode ==PERMISSION_ID){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug", "You Have the permission")
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun GetAddress(longitude: Double, latitude: Double) {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


         address =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

         city = addresses[0].getLocality()
         state = addresses[0].getAdminArea()
         country = addresses[0].getCountryName()
        // postalCode = addresses[0].getPostalCode()
         knownName = addresses[0].getFeatureName()





        Log.e("Address", city + " " + state + " " + country)
    }

    fun DatePickers():Boolean{
        val datePickerDialog =
            DatePickerDialog(
                    this@ApplicationForm1Activity,
                    this@ApplicationForm1Activity,
                    year,
                    month,
                    day
            )
        datePickerDialog.show()
        return true
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        myDay = dayOfMonth
        myYear = year
        myMonth = month+1
        //val calendar: Calendar = Calendar.getInstance()
       // DOB="$myYear/$myMonth/$myDay"
       // dob_tv.text = DOB

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
      //  startActivity(Intent(this, DashBoardActivity::class.java))

        super.onBackPressed()

    }

}