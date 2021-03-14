package com.app.evergrow.Adapter

import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.evergrow.Model.BankModel
import com.app.evergrow.Model.NotificaitonModel
import com.app.evergrow.R
import com.bumptech.glide.Glide

class BankAdapter(context: Context, list: List<BankModel>):
        RecyclerView.Adapter<BankAdapter.ViewHolders>() {

    var context: Context? = null
    lateinit var list: List<BankModel>

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolders {
        val inflater = LayoutInflater.from(context)
        val view: View =
                inflater.inflate(R.layout.layout_bankdesign, null)
        val viewHolder = ViewHolders(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {

        Glide.with(context!!).load(list.get(position).image).into(holder.bank_ivs!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolders(itemView: View): RecyclerView.ViewHolder(itemView){
        var bank_ivs: ImageView?=null


        init {
            bank_ivs=itemView.findViewById(R.id.bank_iv)

        }

    }

    init {

        if (context!=null){
            this.context=context
        }
        this.list=list
    }
}