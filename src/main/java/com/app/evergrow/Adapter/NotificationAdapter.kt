package com.app.evergrow.Adapter

import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.evergrow.Model.NotificaitonModel
import com.app.evergrow.R

class NotificationAdapter(context: Context, list: List<NotificaitonModel>):
        RecyclerView.Adapter<NotificationAdapter.ViewHolders>() {

    var context: Context? = null
    lateinit var list: List<NotificaitonModel>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolders {
        val inflater = LayoutInflater.from(context)
        val view: View =
            inflater.inflate(R.layout.notification_rv_layout, null)
        val viewHolder = ViewHolders(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
         holder.textdescription!!.text="Dear Name of the "+list.get(position).textdescription
        holder.textapplicationno!!.text=list.get(position).applicationno
        holder.textstatus!!.text=list.get(position).applicationstatus

        holder.textapplicationno!!.setOnClickListener {
            holder.textapplicationno!!.setBackgroundColor(Color.GRAY)
            val cm = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = holder.textapplicationno!!.text
            Toast.makeText(context,"Copy",Toast.LENGTH_LONG).show()

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolders(itemView: View): RecyclerView.ViewHolder(itemView){
        var textapplicationno:TextView?=null
        var textdescription:TextView?=null
        var textstatus:TextView?=null

        init {
            textstatus=itemView.findViewById(R.id.notification_stautus_tv)
            textdescription=itemView.findViewById(R.id.notification_descritpion_tv)
            textapplicationno=itemView.findViewById(R.id.notification_applicationno_tv)
        }

    }

    init {

        if (context!=null){
            this.context=context
        }
        this.list=list
    }
}