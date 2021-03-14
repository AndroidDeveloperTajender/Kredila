package com.app.evergrow.Adapter

import a.a.a.a.d.v
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.evergrow.Model.ProductModel
import com.app.evergrow.R
import java.util.*

class ProductsAdapter(context: Context, list: ArrayList<ProductModel>):
    RecyclerView.Adapter<ProductsAdapter.ViewHolders>() {
    var addbooks: ArrayList<ProductModel> = ArrayList<ProductModel>()
    var context: Context? = null
    lateinit var list: ArrayList<ProductModel>
        private var itemClickListener: ItemClickListner? = null

    interface ItemClickListner {
        fun onItemClick(v: View?, pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        val inflater = LayoutInflater.from(context)
        val view: View =
            inflater.inflate(R.layout.layout_productsdialog, null)
        val viewHolder = ViewHolders(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.Showname?.text=list.get(position).title

        holder.CheckBoxAdd?.setOnClickListener {
            val chk = it as CheckBox
            if (chk.isChecked) {
                addbooks.add(list[position])
            } else if (!chk.isChecked) {
                addbooks.remove(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolders(itemView: View): RecyclerView.ViewHolder(itemView){
        var Showname: TextView?=null
        var CheckBoxAdd: CheckBox?=null

        init {
            CheckBoxAdd=itemView.findViewById(R.id.addbookscheckbox)
            Showname=itemView.findViewById(R.id.showaddbookname)


        }

    }
    fun setItemClickListener(ic: ItemClickListner) {
        this.itemClickListener = ic
    }
    init {

        if (context!=null){
            this.context=context
        }
        this.list=list
    }
}