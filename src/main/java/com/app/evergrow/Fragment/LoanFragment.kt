package com.app.evergrow.Fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.app.evergrow.ApplicationForm.ApplicationForm1Activity

import com.app.evergrow.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_loan.view.*

class LoanFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_loan, container, false)

        root.gridlayout.setOnClickListener {

          AleartDialog()

        }

        return root

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
            // Common.bp.purchase(contex,"premiumproducts")
            startActivity(Intent(context, ApplicationForm1Activity::class.java))

            // loanform.IN_APP_PURCHASE_EVENT()
        }

        dialog.show()
    }

}