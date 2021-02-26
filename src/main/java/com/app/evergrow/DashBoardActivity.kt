package com.app.evergrow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import com.app.evergrow.Adapter.NotificationAdapter
import com.app.evergrow.Common.Common
import com.app.evergrow.Fragment.*
import com.app.evergrow.Model.NotificaitonModel
import com.app.evergrow.Model.UserModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.app_bar_dash_board.*
import kotlinx.android.synthetic.main.fragment_notificaiton.view.*
import kotlinx.android.synthetic.main.fragment_track.view.*
import kotlinx.android.synthetic.main.nav_header_dash_board.view.*
import java.io.File
import java.io.IOException
import java.util.*


class DashBoardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var Positionback:Int = 0
    var imageselced = false
    var urione:Uri?=null
    private lateinit var appBarConfiguration: AppBarConfiguration
    var drawerLayout : DrawerLayout?=null
    var userlist= ArrayList<UserModel>()
    var mListenerProfile: OnItemProfileClickListener? = null
    interface OnItemProfileClickListener {
        fun onItemProfileClick(position: Uri)
    }

    fun setOnProfileItemClickListener(listener: OnItemProfileClickListener) {
        mListenerProfile = listener
    }



    var headre :View?=null
    var listnotification= ArrayList<NotificaitonModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)





        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this);
        //headre=navView.getHeaderView(0);
        headre=navView.getHeaderView(0)
        FirebaseFirestore.getInstance().collection("Users")
            .whereEqualTo("uid", FirebaseAuth.getInstance().uid.toString())
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result!!)

                    // Log.e("FRAGMENTPROFILE",documentSnapshot.data.get("FullName").toString())



                       // headre!!.loginusername.text= documentSnapshot.data.get("FullName").toString()

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
                    Common.storeSharedPreference("name",userlist.get(0).Name,this)
                    headre!!.loginusername.text= userlist.get(0).Name

                    // var username=documentSnapshot.data.get("FullName").toString()
                    if (userlist.get(0).profilephoto.isBlank()||userlist.get(0).profilephoto.isEmpty()){
                        Picasso.with(this).load(R.drawable.fblogo).into(headre!!.nav_imageview)
                    }else{
                        Picasso.with(applicationContext).load(userlist.get(0).profilephoto)
                            .into(headre!!.nav_imageview)
                    }
                } else {
                    Log.e("FRAGMENT", "No Data")
                }
            }).addOnFailureListener(OnFailureListener { e ->

            })

        listnotification = ArrayList()
        FirebaseFirestore.getInstance().collection("ApplicationForm")
            .whereEqualTo("uid", FirebaseAuth.getInstance().uid.toString())
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->

                if (task.result.isEmpty){

                }else{
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result!!) {
                            Log.e("FRAGMENT", documentSnapshot.data.get("Applicationno").toString())
                            listnotification.add(
                                NotificaitonModel("Dear ${documentSnapshot.data.get("fullname")}, Thanks for appliying loan.",
                                    "Your Application Number is\n"+documentSnapshot.data.get("Applicationno").toString(),
                                    "Your Application status: Submitted Documents")
                            )
                           // documentSnapshot.data.size
                            badge.setText(listnotification.size.toString())
                        }

                    }else {
                        Log.e("FRAGMENT", "No Data")
                    }
                }
            }).addOnFailureListener(OnFailureListener { e ->

            })




        headre!!.nav_user_emailid.text=FirebaseAuth.getInstance().currentUser?.email

        Picasso.with(applicationContext).load(FirebaseAuth.getInstance().currentUser?.photoUrl).into(
            headre!!.nav_imageview
        )
        drawerLayout = findViewById(R.id.drawer_layout)


        nav_loan.setOnClickListener {
            setFragments(LoanFragment())
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
        }


        bottomNav.selectedItemId=R.id.bnav_home


        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bnav_home -> {
                    setFragments(HomeFragment())

                    true
                }
                R.id.bnav_feedback -> {
                    setFragments(FeedBackFragment())
                    true
                }
                R.id.bnav_support -> {
                    setFragments(SupportFragment())
                    true
                }
                R.id.bnav_account -> {
                    setFragments(MyAccountFragment())
                    true
                }
                R.id.bnav_track -> {
                    setFragments(TrackFragment())
                    true
                }
                else -> false
            }
        }

        notifiation_header_iv.setOnClickListener {
            setFragments(NotificaitonFragment())
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
        }
        support_header_iv.setOnClickListener {
            setFragments(SupportFragment())
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
        }

        nav_account.setOnClickListener {
            setFragments(MyAccountFragment())
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
        }

        nav_notification.setOnClickListener {
            setFragments(NotificaitonFragment())
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
        }
        nav_support.setOnClickListener {
            setFragments(SupportFragment())
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }

        }
        nav_home.setOnClickListener {
            setFragments(HomeFragment())
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }

        }
        nav_track.setOnClickListener {
            setFragments(TrackFragment())
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
        }
        setFragments(HomeFragment())
        nav_logout.setOnClickListener {
            //if (FirebaseAuth.getInstance().currentUser!!.uid!=null) {

            deleteCache(applicationContext)
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this, LoginActivity::class.java))

        }

        nav_feedback.setOnClickListener {
            setFragments(FeedBackFragment())
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
        }

        val toggle: ActionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            ) {
                override fun onDrawerSlide(
                    drawerView: View,
                    slideOffset: Float
                ) {/*
                    val scaleFactor = 7f
                    val slideX = drawerView.width * slideOffset
                    holder.translationX = slideX
                    holder.scaleX = 1 - slideOffset / scaleFactor
                    holder.scaleY = 1 - slideOffset / scaleFactor*/
                    super.onDrawerSlide(drawerView, slideOffset)
                }
            }
        drawerLayout!!.addDrawerListener(toggle)
        drawerLayout!!.setScrimColor(Color.TRANSPARENT)
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorBlack))
        toggle.syncState()

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result = CropImage.getActivityResult(data)
            if (resultCode== RESULT_OK){
                var uri: Uri =result.uri
                //   file= File(urione.path)
                if (imageselced) {
                    imageselced=false
                    try {
                       // urione=uri
                        Common.form1list.put("profile_image", uri.toString())
                        mListenerProfile!!.onItemProfileClick(uri!!)
                        // Picasso.with(this).load(urione).into(f2.photoresult_iv)
                        //f2.photoresult_iv.
                        Log.e("ImageOne", urione.toString())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

    }


    fun ProfileImagePicker(){
        CropImage.activity().start(this);
        imageselced=true

        //  return urione.toString()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    private fun setFragments(fragments: Fragment) {
        val transaction: FragmentTransaction
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_framelayout, fragments)
        transaction.addToBackStack("back")
        transaction.commit()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            } else {
                exitByBackKey()
            }
            //moveTaskToBack(false);
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    protected fun exitByBackKey() {
        val alertbox = androidx.appcompat.app.AlertDialog.Builder(this)
            .setMessage("This Application will help for your financial need Instantly")
            .setPositiveButton("Continue is HelpFul") { arg0, arg1 ->

                // do something when the button is clicked

                //close();
            }
            .setNegativeButton(
                "Exit"
                // do something when the button is clicked
            ) { arg0, arg1 ->
                finish()
                onBackPressed()
            }
            .show()
    }


    override fun onBackPressed() {

        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else if (drawerLayout!!.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawerLayout!!.closeDrawer(GravityCompat.END)
        } else {

            val fragments = supportFragmentManager.fragments
            for (f in fragments) {
                if (f != null && f is HomeFragment) {
                    finish()
                    moveTaskToBack(true)
                    onBackPressed()
                }else{

                }
            }
        }
        super.onBackPressed()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.action_notificaiotn) {
            setFragments(NotificaitonFragment())
            return true
        }
        if (id == R.id.action_support) {
            setFragments(SupportFragment())
            return true
        }


        return super.onOptionsItemSelected(item)

    }
    fun deleteCache(context: Context) {
        try {
            val dir: File = context.getCacheDir()
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory()) {
            val children: Array<String> = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile()) {
            dir.delete()
        } else {
            false
        }
    }

    override fun onResume() {
        super.onResume()

    }

    fun updatename(name: String, emaild: String){
        headre!!.loginusername.text=name
        headre!!.nav_user_emailid.text=emaild

    }
    fun imageUpdate(photo: String){
        Picasso.with(applicationContext).load(photo)
            .into(headre!!.nav_imageview)
    }
}