package com.app.evergrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.goodiebag.pinview.Pinview
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty
import java.util.HashMap
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {
    lateinit var phonenuber_et: TextInputEditText
    lateinit var fullname_et: TextInputEditText
    lateinit var mpinone:EditText
    lateinit var mpintwo:EditText
    lateinit var mpinthree:EditText
    lateinit var mpinfour:EditText

    lateinit var sendOtp_btn: Button
    lateinit var register_btn: Button
    lateinit var login_link: TextView
    lateinit var otp_viewreg: Pinview
    var db = FirebaseFirestore.getInstance()
    var verificationCode=""
    var auth: FirebaseAuth? = null
    var RegisterData: HashMap<String, String> = HashMap<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth=FirebaseAuth.getInstance()
        initView()
    }

    private fun initView() {
        mpinfour=findViewById(R.id.pinfour_et)
        mpinthree=findViewById(R.id.pinthree_et)
        mpinone=findViewById(R.id.pinone_et)
        mpintwo=findViewById(R.id.pintwo_et)

        phonenuber_et=findViewById(R.id.loginphonereg_et)
        fullname_et=findViewById(R.id.fullnamereg_et)
        sendOtp_btn=findViewById(R.id.sendotpreg_btn)
        register_btn=findViewById(R.id.register_btn)
        otp_viewreg=findViewById(R.id.loginregotp)
        login_link=findViewById(R.id.loginlink_tv)

        login_link.setOnClickListener {
            startActivity(Intent(this,LoginPhnActivity::class.java))
        }

        sendOtp_btn.setOnClickListener {
            if (fullname_et.text.toString().isEmpty()||fullname_et.text.toString().isBlank()){
                Toasty.warning(this,"Please Enter Full Name",Toasty.LENGTH_LONG).show()
            }else if (phonenuber_et.text.toString().isEmpty()||phonenuber_et.text.toString().isBlank()){
                Toasty.warning(this,"Please Enter Mobile Number",Toasty.LENGTH_LONG).show()
            }else if (mpinone.text.toString().isEmpty()&&mpintwo.text.toString().isEmpty()&&
                mpinthree.text.toString().isEmpty()&&mpinfour.text.toString().isEmpty()){
                Toasty.warning(this,"Please Enter 4 Digits Mpin",Toasty.LENGTH_LONG).show()
            }
            else{
                SendOtp(phonenuber_et.text.toString())
            }
        }

        mpinone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // specify length of your editext here to move on next edittext

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (mpinone.text.toString().trim().length == 1) {
                    mpintwo.requestFocus()
                }
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        mpintwo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // specify length of your editext here to move on next edittext

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (mpintwo.text.toString().trim().length == 1) {
                    mpinthree.requestFocus()
                }else if (mpintwo.text.toString().trim().length==0){
                    mpinone.requestFocus()
                }

            }
            override fun afterTextChanged(editable: Editable) {}
        })
        mpinthree.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // specify length of your editext here to move on next edittext

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (mpinthree.text.toString().trim().length == 1) {
                    mpinfour.requestFocus()
                }else if (mpinthree.text.toString().trim().length==0){
                    mpintwo.requestFocus()
                }

            }
            override fun afterTextChanged(editable: Editable) {}
        })
        mpinfour.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (mpinfour.text.toString().trim().length==1){
                    mpinfour.requestFocus()
                }
                else if (mpinfour.text.toString().trim().length==0){
                    mpinthree.requestFocus()
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mpinfour.text.toString().trim().length==1){
                    mpinfour.requestFocus()
                }
                else if (mpinfour.text.toString().trim().length==0){
                    mpinthree.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (mpinfour.text.toString().trim().length==1){
                    mpinfour.requestFocus()
                }
                else if (mpinfour.text.toString().trim().length==0){
                    mpinthree.requestFocus()
                }
            }

        })
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
                Toast.makeText(this@RegisterActivity, "verification completed", Toast.LENGTH_SHORT)
                    .show()
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    otp_viewreg.setValue(code)
                    val credential = PhoneAuthProvider.getCredential(verificationCode, code)
                    otp_viewreg.value=code
                    sendOtp_btn.visibility=View.GONE
                    register_btn.visibility=View.VISIBLE
                    SigninWithPhone(credential)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@RegisterActivity, "verification failed", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)

                otp_viewreg.visibility= View.VISIBLE

                verificationCode = s
                Toast.makeText(this@RegisterActivity, "Code sent", Toast.LENGTH_SHORT).show()
            }
        }

    private fun SigninWithPhone(credential: PhoneAuthCredential) {
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {

                    StoreData(auth!!.uid.toString())
                  //  startActivity(Intent(applicationContext, DashBoardActivity::class.java))
                } else {
                    Toast.makeText(this@RegisterActivity, "Incorrect OTP", Toast.LENGTH_SHORT).show()
                }
            }).addOnFailureListener(OnFailureListener { })
    }
    private fun StoreData(uid: String,) {
        var mpin:String=mpinone.text.toString()+""+mpintwo.text.toString()+""+mpinthree.text.toString()+""+mpinfour.text.toString()
        // var userid=FirebaseAuth.getInstance().currentUser
        RegisterData.put("FullName", fullname_et.text.toString())
        RegisterData.put("MobileNumber", "")
        RegisterData.put("Country", "")
        RegisterData.put("emailid", "")
        RegisterData.put("profilephoto", "")
        RegisterData.put("State", "")
        RegisterData.put("uid", uid)
        RegisterData.put("mpin",mpin)

        db.collection("Users").document(uid).set(RegisterData)
            .addOnSuccessListener(OnSuccessListener<Void?> {
//                mobilebumberreg_et.text
                //    firstname.setText("")
                //lastname.setText("")
                Toasty.success(this,"Successfully Registred",Toasty.LENGTH_LONG).show()
                startActivity(Intent(applicationContext, DashBoardActivity::class.java))
            }).addOnFailureListener(OnFailureListener {
                Toast.makeText(
                    this@RegisterActivity,
                    "Registration Failed...Please Try Again Later...",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }
}