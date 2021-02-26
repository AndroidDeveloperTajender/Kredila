package com.app.evergrow.Fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.evergrow.DashBoardActivity
import com.app.evergrow.Model.UserModel
import com.app.evergrow.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_my_account.*
import kotlinx.android.synthetic.main.fragment_my_account.view.*
import java.util.*

class MyAccountFragment : Fragment(),DashBoardActivity.OnItemProfileClickListener {
    //val userdetails = HashMap<String, Any>()
    var userlist= ArrayList<UserModel>()
    var db = FirebaseFirestore.getInstance()
    private var firebaseStore: FirebaseStorage? = null
    var downloadUrl:Uri?=null
    var root: View? = null
    var RegisterData: HashMap<String, String> = HashMap<String, String>()

     var uri:Uri?=null


    var loanform=DashBoardActivity()
    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         root= inflater.inflate(R.layout.fragment_my_account, container, false)
        userlist= ArrayList()


        loanform.setOnProfileItemClickListener(this)
       root!!.profilephoto.setOnClickListener {
            loanform.ProfileImagePicker()
        }


        root!!.edit_iv.setOnClickListener {
            loanform.ProfileImagePicker()
        }

      //  root.TV_display_name.text=""
       // root.TV_country.text=""
       // root.TV_display_email.text=""
        //root.TV_number.text=""
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

                    if (userlist.get(0).profilephoto==""){
                        Picasso.with(context).load(R.drawable.fblogo)
                            .into(root!!.profilephoto)
                    }else {
                        Picasso.with(context).load(userlist.get(0).profilephoto)
                            .into(root!!.profilephoto)
                    }

                    root!!.coutry_et.setText(userlist.get(0).Country)
                    root!!.username_et.setText(userlist.get(0).Name)
                    root!!.mobile_et.setText(userlist.get(0).Mobilenumber)
                    root!!.email_et.setText(userlist.get(0).emailid)
                    root!!.state_et.setText(userlist.get(0).state)
                    root!!.mpin_et.setText(userlist.get(0).mpin)


                } else {
                    Log.e("FRAGMENT", "No Data")
                }
            }).addOnFailureListener(OnFailureListener { e ->

            })

        root!!.fab_editbtn.setOnClickListener {

            root!!.fab_editbtn.setVisibility(View.GONE)
            root!!.profileedit_btn.visibility=View.VISIBLE
            root!!.edit_iv.visibility=View.VISIBLE
            root!!.coutry_et.isEnabled=true
            root!!.username_et.isEnabled=true
            root!!.mobile_et.isEnabled=true
            root!!.mpin_et.isEnabled=true
            root!!.state_et.isEnabled=true
        }

        root!!.profileedit_btn.setOnClickListener {


            if (mpin_et.text.toString().length == 4) {
            var userid = FirebaseAuth.getInstance().currentUser
            db.collection("Users").document(userid!!.uid).update(
                mapOf(
                    "FullName" to root!!.username_et.text.toString(),
                    "Country" to root!!.coutry_et.text.toString(),
                    "emailid" to root!!.email_et.text.toString(),
                    "MobileNumber" to root!!.mobile_et.text.toString(),
                    //  "profilephoto" to "",
                    "State" to root!!.state_et.text.toString(),
                    "mpin" to root!!.mpin_et.text.toString()
                )
            )
                .addOnCompleteListener {

                    if (it.isComplete) {
                        root!!.edit_iv.visibility = View.GONE
                        root!!.fab_editbtn.setVisibility(View.VISIBLE)
                        root!!.profileedit_btn.visibility = View.GONE
                        root!!.coutry_et.isEnabled = false
                        root!!.username_et.isEnabled = false
                        root!!.mobile_et.isEnabled = false
                        root!!.mpin_et.isEnabled = false
                        root!!.state_et.isEnabled = false

                        loanform.updatename(username_et.text.toString(), email_et.text.toString(),)
                    } else {
                        Log.e("ERROR", "No Update")
                    }

                }

                .addOnFailureListener{ e->
                    Log.e("EXCEPTION", e.toString())
                }
            }else{
                Toasty.warning(requireActivity().applicationContext,"Please Enter 4 Digits Mpin",Toasty.LENGTH_LONG).show()
            }
        }
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loanform= context as DashBoardActivity
    }

    override fun onItemProfileClick(position: Uri) {

        //var bitmap=MediaStore.Images.Media.getBitmap(contentRes)
        Picasso.with(context).load(position).into(root!!.profilephoto)
        UploadImage(position)
    }

    private fun UploadImage(uri: Uri) {
        if(uri != null){
            var pd=ProgressDialog(context)
            pd.setTitle("Profile Image Uploading Please Wait..")
            pd.setCancelable(false)
            pd.show()
            val ref = FirebaseStorage.getInstance().reference.child("uploads/" + UUID.randomUUID().toString())
       var uploadtask=  ref?.putFile(uri)
        val urlTask=uploadtask.continueWithTask { task->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw  it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task->
            if (task.isSuccessful){
                val downloadUrl=task.result
                    UploadImageUrl(downloadUrl)
                pd.dismiss()
            }else{

            }
        }.addOnFailureListener { e->
           Log.e("ERROR",e.message.toString())
        }

        }else{
            Toast.makeText(context, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun UploadImageUrl(durl: Uri) {

        RegisterData.put("profilephoto", durl.toString())
        var userid=FirebaseAuth.getInstance().currentUser
        FirebaseFirestore.getInstance().collection("Users").document(userid!!.uid).update(
            "profilephoto", durl.toString()
            )
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    Log.e("RESPONSE","IMAGEUPDATE")

                }else{
                    Log.e("ERROR", "No Update")
                }

            }
            .addOnFailureListener{ e->
                Log.e("EXCEPTION", e.toString())
            }

    }

    private fun addUploadRecordToDb(uri: String){
        val db = FirebaseFirestore.getInstance()

        val data = HashMap<String, Any>()
        data["imageUrl"] = uri

        db.collection("posts")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context, "Saved to DB", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error saving to DB", Toast.LENGTH_LONG).show()
            }
    }


}

