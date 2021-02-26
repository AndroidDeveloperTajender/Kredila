package com.app.evergrow.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.evergrow.Adapter.NotificationAdapter
import com.app.evergrow.ApplicationForm.ApplicationForm1Activity
import com.app.evergrow.Common.Common
import com.app.evergrow.Model.NotificaitonModel

import com.app.evergrow.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_notificaiton.view.*
import kotlinx.android.synthetic.main.fragment_track.view.*
import java.util.ArrayList


class NotificaitonFragment : Fragment() {
    private var gm: GridLayoutManager? = null
    var listnotification= ArrayList<NotificaitonModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_notificaiton, container, false)

        root.notrecord_tv.text="Ohh Dear "+ context?.let {
            Common.getSharePreferenceString("name", it) } +", Your Notification Box is empty Now"

        listnotification = ArrayList()
        FirebaseFirestore.getInstance().collection("ApplicationForm")
            .whereEqualTo("uid", FirebaseAuth.getInstance().uid.toString())
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->

                if (task.result.isEmpty){
                    Log.e("FRAGMENT", "No Data")
                    root.notification_rv.visibility=View.GONE
                    root.notificaitonloan_bg.visibility=View.VISIBLE
                    root.notrecord_tv.visibility=View.VISIBLE
                }else{
                if (task.isSuccessful) {
                    root.notification_rv.visibility=View.VISIBLE
                    root.notrecord_tv.visibility=View.GONE
                    root.notificaitonloan_bg.visibility=View.GONE
                    for (documentSnapshot in task.result!!) {
                        Log.e("FRAGMENT", documentSnapshot.data.get("Applicationno").toString())
                listnotification.add(
                    NotificaitonModel(" ${documentSnapshot.data.get("fullname")}, Thanks for appliying loan.",
                        "Your Application Number is\n"+documentSnapshot.data.get("Applicationno").toString(),
                    "Your Application status: Submitted Documents"))
                        val adapters =
                            context?.let { NotificationAdapter(it, listnotification) }

                        root.notification_rv.setHasFixedSize(true)

                        gm = GridLayoutManager(context, 1)
                        root.notification_rv!!.layoutManager=gm
                        root.notification_rv.adapter=adapters
                    }

                }else {
                    Log.e("FRAGMENT", "No Data")
                }
                }
            }).addOnFailureListener(OnFailureListener { e ->

            })


        root.notificaitonloan_bg.setOnClickListener {
            startActivity(Intent(context,ApplicationForm1Activity::class.java))
        }


        return root
    }

}