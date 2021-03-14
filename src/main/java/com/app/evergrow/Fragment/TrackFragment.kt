package com.app.evergrow.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.evergrow.ApplicationForm.ApplicationForm1Activity
import com.app.evergrow.Common.Common
import com.app.evergrow.Model.UserModel


import com.app.evergrow.R
import com.app.evergrow.TrackedActivity

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_my_account.view.*
import kotlinx.android.synthetic.main.fragment_track.view.*
import java.util.*

class TrackFragment : Fragment() {
    var userlist= ArrayList<UserModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_track, container, false)

        FirebaseFirestore.getInstance().collection("Users")
            .whereEqualTo("uid", FirebaseAuth.getInstance().uid.toString())
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    val list: MutableList<String> = ArrayList()
                    list.add("Please Choose City")
                    for (documentSnapshot in task.result!!)

                    // Log.e("FRAGMENTPROFILE",documentSnapshot.data.get("FullName").toString())
                        userlist.add(
                            UserModel(
                                documentSnapshot.data.get("FullName").toString(),
                                documentSnapshot.data.get("MobileNumber").toString(),
                                documentSnapshot.data.get("Country").toString(),
                                documentSnapshot.data.get("emailid").toString(),
                                documentSnapshot.data.get("profilephoto").toString(),
                                documentSnapshot.data.get("State").toString(),
                            documentSnapshot.data.get("mpin").toString()
                            )
                        )
                    root.track_name_tv.setText("Dear "+userlist.get(0).Name)
                    root.notrack_name_tv.setText("Dear "+userlist.get(0).Name)


                } else {
                    Log.e("FRAGMENT", "No Data")
                }
            }).addOnFailureListener(OnFailureListener { e ->

            })




        root.loan_btn.setOnClickListener {
            startActivity(Intent(context, ApplicationForm1Activity::class.java))
        }

        FirebaseFirestore.getInstance().collection("ApplicationForm")
            .whereEqualTo("uid",FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.result.isEmpty){

                    root.notrackloan_ll.visibility=View.GONE
                    root.trackloan_ll.visibility = View.VISIBLE
                }else{


                    if (task.isSuccessful) {


                        root.trackloan_ll.visibility = View.VISIBLE
                        root.notrackloan_ll.visibility=View.GONE
                        //root.loan_btn.visibility = View.GONE
                    }else {
                        for (documentSnapshot in task.result!!) {

                            if (documentSnapshot.data.isEmpty()) {
                                root.notrackloan_ll.visibility=View.GONE
                                root.trackloan_ll.visibility = View.VISIBLE
                                // root.loan_btn.visibility = View.VISIBLE
                                // Log.e("FRAGMENT", "No Data")
                            } else {
                                root.notrackloan_ll.visibility=View.VISIBLE
                                Log.e("FRAGMENT", documentSnapshot.data.toString())
                            }
                        }
                    }
                }
            }).addOnFailureListener(OnFailureListener { e ->
                Log.e("FRAGMENT",e.toString())
            })

        root.track_btn.setOnClickListener {
            FirebaseFirestore.getInstance().collection("ApplicationForm")
                .whereEqualTo("Applicationno",root.application_et.text.toString())
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->

                    if (task.result.isEmpty){

                        root.notrackloan_ll.visibility=View.GONE
                        root.trackloan_ll.visibility = View.VISIBLE
                    }else {
                        if (task.isSuccessful) {

                                for (documentSnapshot in task.result!!) {

                                    if (documentSnapshot.data.isEmpty()) {
                                        root.trackloan_iv.visibility = View.VISIBLE
                                        root.trackloan_ll.visibility = View.GONE
                                        root.notrackloan_ll.visibility = View.VISIBLE
                                        Log.e("FRAGMENT", "No Data")
                                    } else {
                                        root.trackloan_iv.visibility = View.VISIBLE
                                        root.notrackloan_ll.visibility = View.GONE
                                        root.trackloan_ll.visibility = View.GONE
                                        startActivity(Intent(context, TrackedActivity::class.java))
                                     //   root.root_track.setBackgroundResource(R.drawable.home_get_loan)

                                    }
                                }
                        }
                    }

                }).addOnFailureListener(OnFailureListener { e ->
                    Log.e("FRAGMENT",e.toString())
                })
        }
        return root
    }

}