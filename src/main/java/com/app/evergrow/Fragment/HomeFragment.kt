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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.evergrow.Adapter.BankAdapter
import com.app.evergrow.Adapter.HomeBannerAdapter
import com.app.evergrow.Adapter.ProductsAdapter
import com.app.evergrow.ApplicationForm.ApplicationForm1Activity
import com.app.evergrow.Common.Common
import com.app.evergrow.Common.PicassoImageLoadingService
import com.app.evergrow.Model.BankModel
import com.app.evergrow.Model.ProductModel
import com.app.evergrow.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_notificaiton.view.*
import ss.com.bannerslider.Slider
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private var gm: GridLayoutManager? = null

    var bank_rvlist=java.util.ArrayList<BankModel>()

    var dialoglist= java.util.ArrayList<ProductModel>()
  //  var dialoglist=ArrayList<ProductModel>()
    override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_home, container, false)

        getList(root)

      bank_rvlist.add(BankModel(R.drawable.logoab))
      bank_rvlist.add(BankModel(R.drawable.piramal))
      bank_rvlist.add(BankModel(R.drawable.shinhanbank))
      bank_rvlist.add(BankModel(R.drawable.adanic))
      bank_rvlist.add(BankModel(R.drawable.dsbank))
      bank_rvlist.add(BankModel(R.drawable.o))
      bank_rvlist.add(BankModel(R.drawable.yesbank))
      bank_rvlist.add(BankModel(R.drawable.ziploan))



      root.landkart_ll.setOnClickListener {
            AleartDialog()
        }

        root.duc_ll.setOnClickListener {
            AleartDialog()
        }

        root.marquee_tv.isSelected=true
      root.bank_rv.setHasFixedSize(true)
      gm = GridLayoutManager(context, 1)
      var layout=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
      root.bank_rv!!.layoutManager=layout
      var bankadapter= BankAdapter(requireContext(),bank_rvlist)
        root.bank_rv.adapter=bankadapter

        root.iploanll.setOnClickListener {
            dialoglist.add(ProductModel("Low Salary Personal Loan (Min 15k)", false))
            dialoglist.add(ProductModel("Without Salary Slips Personal Loan ", false))
            dialoglist.add(ProductModel("Proprietor Employee Personal Loan", false))
            dialoglist.add(ProductModel("Partnership employee Personal Loan", false))
            dialoglist.add(ProductModel("-1 CIBIL Employee Personal Loan", false))
            dialoglist.add(ProductModel("High Profile Personal Loan", false))
            productsDialog(dialoglist)
        }
        root.ibloanll.setOnClickListener {

            dialoglist.add(ProductModel("Both Rented Profile Business Loan", false))
            dialoglist.add(ProductModel("Saving A/c Business Loan", false))
            dialoglist.add(ProductModel("Without ITR/GST/CA Business Loan", false))
            dialoglist.add(ProductModel("1 Credit Score Business Loan", false))
            dialoglist.add(ProductModel("Low Banking Business Loan", false))
            dialoglist.add(ProductModel("Low Banking Business Loan", false))
            dialoglist.add(ProductModel("Without Vintage Business Loan", false))
            dialoglist.add(ProductModel("High Profile Business Loan", false))
            productsDialog(dialoglist)

        }
        root. prloanll.setOnClickListener {
            dialoglist.add(ProductModel("LAAL DORA LAP", false))
            dialoglist.add(ProductModel("Un-authorize LAP", false))
            dialoglist.add(ProductModel("without map LAP", false))
            dialoglist.add(ProductModel("Commercial LAP", false))
            dialoglist.add(ProductModel("Commercial LAP", false))
            dialoglist.add(ProductModel("Industrial LAP", false))
            dialoglist.add(ProductModel("Rasidential LAP", false))
            dialoglist.add(ProductModel("CashIncome LAP", false))
            dialoglist.add(ProductModel("Rental Income LAP", false))
            dialoglist.add(ProductModel("Khasra/Khatuni LAP", false))
            dialoglist.add(ProductModel("Khasra/Khatuni LAP", false))
            dialoglist.add(ProductModel("Form House LAP", false))
            dialoglist.add(ProductModel("All Type of Plots LAP", false))
            dialoglist.add(ProductModel("Builder Floor LAP", false))
            dialoglist.add(ProductModel("Home Loan(All Types)", false))
            dialoglist.add(ProductModel("Builder LAP", false))

            productsDialog(dialoglist)
            //AleartDialog()
        }
        root.insurancell.setOnClickListener {
            // OpenApplicationForm()
            dialoglist.add(ProductModel("Life Insurence", false))
            dialoglist.add(ProductModel("Health Insurence", false))
            dialoglist.add(ProductModel("Car Insurence", false))
            dialoglist.add(ProductModel("Term Insurence", false))

            productsDialog(dialoglist)
        }


        root. bankaccountll.setOnClickListener {
            dialoglist.add(ProductModel("Saving A/C", false))
            dialoglist.add(ProductModel("Current A/C", false))
            dialoglist.add(ProductModel("Demat A/C", false))
            productsDialog(dialoglist)
        }
        root. creditcardll.setOnClickListener {
            dialoglist.add(ProductModel("Self Employee Credit Card", false))
            dialoglist.add(ProductModel("Salaried Credit Card", false))
            dialoglist.add(ProductModel("Card to Card Credit Card", false))
            dialoglist.add(ProductModel("FD Against Credit Card", false))
            dialoglist.add(ProductModel("Saving A/c on Credit Card", false))

            productsDialog(dialoglist)
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


    fun productsDialog(dialoglist: ArrayList<ProductModel>) {
        var dialog=Dialog(requireContext())
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
      //  dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.layoutproduct_recyclerview)

        var btn=dialog.findViewById<Button>(R.id.submit_btn)
        var recyclerview=dialog.findViewById<RecyclerView>(R.id.recyclerview_products)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager=gm
        var adapters=ProductsAdapter(requireContext(), dialoglist)
        recyclerview.adapter=adapters
        val sb = StringBuffer()
        btn.setOnClickListener {
            for(i in adapters.addbooks){
                sb.append(i.title)
                sb.append(",")
                Toast.makeText(context,sb,Toast.LENGTH_LONG).show()
                Common.form1list.put("product_type",sb.toString())
                startActivity(Intent(context,ApplicationForm1Activity::class.java))
            }
        }
        dialog.show()
    }


    private fun getList(root: View) {
        Slider.init(PicassoImageLoadingService(root.context))
        root.banner_slider.setAdapter(HomeBannerAdapter())

    }

    override fun onResume() {
        super.onResume()
     //   gettingYear()
        //  OpenDialog()
    }

    override fun onStart() {
        super.onStart()
    //    gettingYear()
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

    fun OpenApplicationForm(){
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