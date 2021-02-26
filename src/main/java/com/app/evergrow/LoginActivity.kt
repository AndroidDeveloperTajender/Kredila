package com.app.evergrow

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener {
    var name: String? = null
    var RegisterData: HashMap<String, String> = HashMap<String, String>()

    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    var db = FirebaseFirestore.getInstance()
    var selected_country_code:String=""
    var otpnumber:String=""
    var verificationCode=""
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth=FirebaseAuth.getInstance()


        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)


        tc_tv.setPaintFlags(tc_tv.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        tc_tv.setOnClickListener {

            startActivity(Intent(this,TermandConditionActivity::class.java))
        }


        sign_in_button.setOnClickListener {
            signIn()
        }

/*
        registerlink_tv.setOnClickListener {

            var intens=Intent(applicationContext, RegistrationActivity::class.java)
            startActivity(intens)
        }

        sendotp_tv.setOnClickListener {

            if (phonelogin_et.text.toString().isEmpty()){
                Toasty.warning(this, "Please Enter 10 digits Phone Number.", Toasty.LENGTH_LONG).show()
            }
            else{
                SendOtp(selected_country_code + " " + phonelogin_et.text.toString())
            }
        }

        pinview_verify.setPinViewEventListener(PinViewEventListener { pinview, fromUser ->
            otpnumber = pinview.value
        })



        sednotplogin_btn.setOnClickListener {

            if (phonelogin_et.text.toString().isEmpty()){
                Toasty.warning(this, "Please Enter 10 digits Phone Number", Toasty.LENGTH_LONG).show()
            }else if (otpnumber.isEmpty()){
                Toasty.warning(this, "Please Enter 6 digits Otp Number", Toasty.LENGTH_LONG).show()
            }
            else{

            }
         //   var otpintent=Intent(applicationContext,OTPActivity::class.java)
          //  otpintent.putExtra("Phno",phone_et.text.toString())
           // startActivity(otpintent)
        }*/
    }


    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth!!.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                //    Log.e("LoginActivity",acct.email.toString())
               // Log.e("LoginActivity",acct.displayName.toString())
               // Log.e("LoginActivity","Uid"+FirebaseAuth.getInstance().uid)


                CheckUserExistorNot(FirebaseAuth.getInstance().uid, acct)


                //StoreData(FirebaseAuth.getInstance().uid.toString(),acct)

                //startActivity(HomeActivity.getLaunchIntent(this))
            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun StoreData(uid: String, acct: GoogleSignInAccount) {
       // var userid=FirebaseAuth.getInstance().currentUser
        RegisterData.put("FullName", acct.displayName.toString())
        RegisterData.put("MobileNumber", "")
        RegisterData.put("Country", "")
        RegisterData.put("emailid", acct.email.toString())
        RegisterData.put("profilephoto", "")
        RegisterData.put("State", "")
        RegisterData.put("uid", uid)

        db.collection("Users").document(uid).set(RegisterData)
            .addOnSuccessListener(OnSuccessListener<Void?> {
//                mobilebumberreg_et.text
                //    firstname.setText("")
                //lastname.setText("")
                startActivity(Intent(applicationContext, DashBoardActivity::class.java))
            }).addOnFailureListener(OnFailureListener {
                Toast.makeText(
                    this@LoginActivity,
                    "Registration Failed...Please Try Again Later...",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }
    private fun CheckUserExistorNot(uid: String?, acct: GoogleSignInAccount) {

        FirebaseFirestore.getInstance().collection("Users")
            .whereEqualTo("uid",uid)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isComplete) {

                    if (task.result.isEmpty) {
                        uid?.let { StoreData(it, acct) }
                        Log.e("LoginActivity", "No User Exist")
                    } else {

                        for (documentSnapshot in task.result!!) {
                            // Log.e("LoginActivity", documentSnapshot.data["uid"].toString())

                            if (documentSnapshot.data["uid"].toString()
                                    .isNotEmpty() || documentSnapshot.data["uid"].toString()
                                    .isNotBlank()
                            ) {

                                startActivity(
                                    Intent(
                                        applicationContext,
                                        DashBoardActivity::class.java
                                    )
                                )
                                //  Log.e("LoginActivity", "User Exist")
                            } else {
                                Toasty.warning(this,"Please First Registration ..",Toasty.LENGTH_LONG).show()
                                //    Log.e("LoginActivity", "No User Exist")

                            }

                            /* if (documentSnapshot.data.) {
                            Log.e("LoginActivity", "No User Exist")


                        } else {
                            Log.e("LoginActivity", "User Exist")
                        }*/
                        }
                        Log.e("LoginActivity", " User Exist")
                    }

                } else {
                    StoreData(FirebaseAuth.getInstance().uid.toString(), acct)
                    Log.e("LoginActivity", "No User Exist")
                }
            }).addOnFailureListener(OnFailureListener { e ->
                Log.e("LoginActivity", e.toString())
            })
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
        //    startActivity(HomeActivity.getLaunchIntent(this))
         //   finish()
        }
    }

    private fun SendOtp(phonenumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91 $phonenumber",  // Phone number to verify
            60,  // Timeout duration
            TimeUnit.SECONDS,  // Unit of timeout
            TaskExecutors.MAIN_THREAD,  // Activity (for callback binding)
            mCallback
        )
        auth = FirebaseAuth.getInstance()
    }
    private val mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Toast.makeText(this@LoginActivity, "verification completed", Toast.LENGTH_SHORT)
                    .show()
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                //    pinview_verify.setValue(code)
                    val credential = PhoneAuthProvider.getCredential(verificationCode, code)
               //     pinview_verify.value=code
                    SigninWithPhone(credential)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@LoginActivity, "verification fialed", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationCode = s
                Toast.makeText(this@LoginActivity, "Code sent", Toast.LENGTH_SHORT).show()
            }
        }
    private fun SigninWithPhone(credential: PhoneAuthCredential) {
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, DashBoardActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, "Incorrect OTP", Toast.LENGTH_SHORT).show()
                }
            }).addOnFailureListener(OnFailureListener { })
    }/*
    fun onCountryPickerClick(view: View?) {
        ccp.setOnCountryChangeListener { //Alert.showMessage(RegistrationActivity.this, ccp.getSelectedCountryCodeWithPlus());
            selected_country_code = ccp.selectedCountryCodeWithPlus
           // ccp.set
            Log.e("SelectedCountry", selected_country_code)

        }
    }
    */

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
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
        val alertbox = AlertDialog.Builder(this)
            .setMessage("This Application will help for your financial need Instantly")
            .setPositiveButton("Exit") { arg0, arg1 ->

                // do something when the button is clicked
                finish()
                //close();
                onBackPressed()
            }
            .setNegativeButton(
                "Continue is HelpFul" // do something when the button is clicked
            ) { arg0, arg1 -> }
            .show()
    }

    override fun onBackPressed() {

                    moveTaskToBack(true)
                    finish()
                    onStop()




        super.onBackPressed()

    }
}