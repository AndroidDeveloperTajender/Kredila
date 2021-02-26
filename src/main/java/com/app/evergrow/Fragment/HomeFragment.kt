package com.app.evergrow.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.app.evergrow.Adapter.HomeBannerAdapter
import com.app.evergrow.ApplicationForm.ApplicationForm1Activity
import com.app.evergrow.Common.PicassoImageLoadingService
import com.app.evergrow.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.view.*
import ss.com.bannerslider.Slider


class HomeFragment : Fragment() {

    val itemList:MutableList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_home, container, false)

        getList(root)

        root.landkart_ll.setOnClickListener {
            AleartDialog()
        }

        root.duc_ll.setOnClickListener {
            AleartDialog()
        }

        root.marquee_tv.isSelected=true

        root.iploanll.setOnClickListener {
            AleartDialog()
        }
        root.ibloanll.setOnClickListener {
            AleartDialog()
        }
        root. csloanll.setOnClickListener {
            AleartDialog()
        }

        root. isloanll.setOnClickListener {
            AleartDialog()
        }
        root. witrgstloanll.setOnClickListener {
            AleartDialog()
        }

        root.cdloanll.setOnClickListener {
           // OpenApplicationForm()
            AleartDialog()
        }
        root.applicationloan.setOnClickListener {
            // OpenApplicationForm()
            AleartDialog()
        }
        root.dailyloan.setOnClickListener {
            // OpenApplicationForm()
            AleartDialog()
        }
        root.witrloan.setOnClickListener {
            // OpenApplicationForm()
            AleartDialog()
        }



        return root
    }
    private fun getList(root: View) {
        Slider.init(PicassoImageLoadingService(root.context))
        root.banner_slider.setAdapter(HomeBannerAdapter())

    }

    override fun onResume() {
        super.onResume()
        gettingYear()
        //  OpenDialog()
    }

    override fun onStart() {
        super.onStart()
        gettingYear()
    }


    fun gettingYear(){
        val dialog = context?.let { Dialog(it) }
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.layout_newyeardialog)
        var btn=dialog.findViewById<Button>(R.id.dissmissbtn)
        btn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun   OpenApplicationForm(){
        activity?.let{
            val intent = Intent(it, ApplicationForm1Activity::class.java)
            it.startActivity(intent)
        }
    }

    fun AleartDialog(){
        val dialog = context?.let { Dialog(it) }
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.homedialog_layout)
        val name = dialog!!.findViewById(R.id.headerusername_tv) as TextView
        name.text = "Dear  ${FirebaseAuth.getInstance().currentUser!!.displayName}"
        val yesBtn = dialog!!.findViewById(R.id.dialogdismissbtn) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
            OpenApplicationForm()
        }

        dialog.show()
    }

    private fun OpenDialog() {
        val builder = AlertDialog.Builder(context)
        //set title for alert dialog
        builder.setTitle("Information")
        //set message for alert dialog
        builder.setMessage("You Have to Submit All Correct Details and Documents!")
        builder.setIcon(android.R.drawable.ic_dialog_alert)


        //performing cancel action
        builder.setNeutralButton("Cancel") { dialogInterface, which ->

           // Toast.makeText(context, "clicked cancel\n operation cancel", Toast.LENGTH_LONG).show()
        }
        builder.setPositiveButton("Continue"){ dialogInterface, which ->

        }

        // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

    }





}