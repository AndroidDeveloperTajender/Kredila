package com.app.evergrow.Fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.app.evergrow.Common.Common
import com.app.evergrow.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_feed_back.view.*
import kotlinx.android.synthetic.main.homedialog_layout.view.*
import java.util.HashMap


class FeedBackFragment : Fragment() {
    var FeedbackData: HashMap<String, String> = HashMap<String, String>()
    var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_feed_back, container, false)


        root.feedback_btn.setOnClickListener {

            if (root.fd_name_et.text.toString().isEmpty()){

                context?.let { it1 -> Toasty.warning(it1,"Please Enter Name",Toasty.LENGTH_LONG).show() }
                //com.earlypay.Common.Common.WarningMsg(context,"Please Enter Name",Toasty.LENGTH_LONG)

            }else if (root.fd_email_et.text.toString().isEmpty()){
                context?.let { it1 -> Toasty.warning(it1,"Please Enter Emailid",Toasty.LENGTH_LONG).show() }
            }else if (root.fd_message_et.text.toString().isEmpty()){
                context?.let { it1 -> Toasty.warning(it1,"Please Enter Message",Toasty.LENGTH_LONG).show() }
            }else{
                StoreData(root)
            }

        }

        return root
    }
    private fun StoreData(root: View) {
        var userid= FirebaseAuth.getInstance().currentUser
        FeedbackData.put("FullName",root. fd_name_et.text.toString())
        FeedbackData.put("Emailid", root.fd_email_et.text.toString())
        FeedbackData.put("Message", root.fd_message_et.text.toString())
        FeedbackData.put("uid",userid!!.uid)

        db.collection("Feedback").document(userid!!.uid).set(FeedbackData)
            .addOnSuccessListener(OnSuccessListener<Void?> {
               // mobilebumberreg_et.text
              root.  fd_name_et.setText("")
                root. fd_message_et.setText("")
                root.fd_email_et.setText("")

                AleartDialog()

                //lastname.setText("")
                //startActivity(Intent(applicationContext, HomeActivity::class.java))
            }).addOnFailureListener(OnFailureListener {
                Toast.makeText(
                    context,
                    "Registration Failed...Please Try Again Later...",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }


    fun AleartDialog(){
        val dialog = context?.let { Dialog(it) }
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.homedialog_layout)
        val name = dialog!!.findViewById(R.id.headerusername_tv) as TextView
        name.text = "Dear  ${FirebaseAuth.getInstance().currentUser!!.displayName} "

        val message=dialog!!.findViewById(R.id.textMessage)as TextView
        message.text="Successfully Submit Your Feedback Form Thank You"
        val yesBtn = dialog!!.findViewById(R.id.dialogdismissbtn) as Button

        yesBtn.setOnClickListener {
            dialog.dismiss()
            // Common.bp.purchase(contex,"premiumproducts")


            // loanform.IN_APP_PURCHASE_EVENT()
        }

        dialog.show()
    }
}