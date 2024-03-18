package com.example.shifttestproject.presentation

import android.app.DownloadManager.Request
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.privacysandbox.tools.core.model.Method
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shifttestproject.R
import com.example.shifttestproject.domain.User
import com.squareup.picasso.Picasso
import org.json.JSONObject


class ItemAdapter(var items: ArrayList<User>, val context: Context): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val llUser: LinearLayout = view.findViewById(R.id.ll_user)
        val imgPhoto: ImageView = view.findViewById(R.id.img_photo)
        val txtFIO: TextView = view.findViewById(R.id.txt_item_FIO)
        val txtAddress: TextView = view.findViewById(R.id.txt_item_address)
        val txtPhoneNumber: TextView = view.findViewById(R.id.txt_item_phone_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(items[position].image).into(holder.imgPhoto)
        holder.txtFIO.text = items[position].FIO
        holder.txtAddress.text = items[position].address
        holder.txtPhoneNumber.text = items[position].phoneNumber

        holder.llUser.setOnClickListener{
            context.startActivity(Intent(context, ItemActivity::class.java))
        }
    }
}