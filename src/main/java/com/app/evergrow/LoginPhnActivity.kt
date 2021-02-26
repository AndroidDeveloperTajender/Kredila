package com.app.evergrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.goodiebag.pinview.Pinview
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_login_phn.*
import java.util.concurrent.TimeUnit

class LoginPhnActivity : AppCompatActivity() {

    lateinit var phonenuber: TextInputEditText
    lateinit var sendOtp_btn:Button
    lateinit var login_btn:Button
    lateinit var Register:TextView
    lateinit var otp_view:Pinview
    var verificationCode=""
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_phn)
        auth=FirebaseAuth.getInstance()
        initView()
    }

    private fun initView() {
       phonenuber=findViewById(R.id.loginphone_et)
        sendOtp_btn=findViewById(R.id.sendotp_btn)
        login_btn=findViewById(R.id.login_btn)
        otp_view=findViewById(R.id.loginotp)
        Register=findViewById(R.id.registerlink_tv)

        Register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        mpinmpinlink_tv.setOnClickListener {
            startActivity(Intent(this,MPINActivity::class.java))
        }
        sendOtp_btn.setOnClickListener {

            if (phonenuber.text!!.isBlank()||phonenuber.text!!.isEmpty()){

            }else{
                SendOtp(phonenuber.text.toString())
            }


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
                Toast.makeText(this@LoginPhnActivity, "verification completed", Toast.LENGTH_SHORT)
                    .show()
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    otp_view.setValue(code)
                    val credential = PhoneAuthProvider.getCredential(verificationCode, code)
                    otp_view.value=code
                    SigninWithPhone(credential)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@LoginPhnActivity, "verification failed", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                sendOtp_btn.visibility= View.GONE
                login_btn.visibility= View.VISIBLE
                otp_view.visibility=View.VISIBLE

                verificationCode = s
                Toast.makeText(this@LoginPhnActivity, "Code sent", Toast.LENGTH_SHORT).show()
            }
        }
    private fun SigninWithPhone(credential: PhoneAuthCredential) {
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, DashBoardActivity::class.java))
                } else {
                    Toast.makeText(this@LoginPhnActivity, "Incorrect OTP", Toast.LENGTH_SHORT).show()
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
}