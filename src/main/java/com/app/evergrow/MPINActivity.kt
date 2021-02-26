package com.app.evergrow

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import es.dmoral.toasty.Toasty


class MPINActivity : AppCompatActivity() {

    lateinit var mpinone: EditText
    lateinit var mpintwo: EditText
    lateinit var mpinthree: EditText
    lateinit var mpinfour: EditText
    lateinit var mpinloginlink_tv:TextView


    lateinit var mpinlogin_btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_p_i_n)

        initView()
    }

    private fun initView() {
        mpinfour=findViewById(R.id.pinfourm_et)
        mpinthree=findViewById(R.id.pinthreem_et)
        mpinone=findViewById(R.id.pinonem_et)
        mpintwo=findViewById(R.id.pintwom_et)
        mpinlogin_btn=findViewById(R.id.loginmpin_btn)
        mpinloginlink_tv=findViewById(R.id.mpinloginlink_tv)


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

        mpinloginlink_tv.setOnClickListener {
            startActivity(Intent(this, LoginPhnActivity::class.java))
        }


        mpinlogin_btn.setOnClickListener {

            if (mpinone.text.toString().isEmpty()&&mpintwo.text.toString().isEmpty()&&
                mpinthree.text.toString().isEmpty()&&mpinfour.text.toString().isEmpty()){
                Toasty.warning(this, "Please Enter 4 Digits Mpin", Toasty.LENGTH_LONG).show()
            }
            else{
                var mpin:String=mpinone.text.toString()+""+mpintwo.text.toString()+""+mpinthree.text.toString()+""+mpinfour.text.toString()

                CheckMpin(mpin)
               // SendOtp(phonenuber_et.text.toString())
            }
        }
    }

    private fun CheckMpin(mpin: String) {
        FirebaseFirestore.getInstance().collection("Users")
            .whereEqualTo("mpin", mpin)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->

                if (task.result.isEmpty) {

                } else {
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result!!) {
                            Log.e("FRAGMENT", documentSnapshot.data.get("mpin").toString())
                            startActivity(Intent(this, DashBoardActivity::class.java))
                        }

                    } else {
                        Toasty.warning(this, "Please Enter Valid Mpin", Toasty.LENGTH_LONG).show()
                        Log.e("FRAGMENT", "No Data")
                    }
                }
            }).addOnFailureListener(OnFailureListener { e ->

            })
    }
}