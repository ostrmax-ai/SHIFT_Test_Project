package com.example.shifttestproject.presentation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shifttestproject.R
import com.example.shifttestproject.domain.User
import com.example.shifttestproject.domain.UserResponse
import com.squareup.picasso.Picasso


class ItemAdapter(var items: List<User>, val context: Context): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

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

        val name = items[position].name
        val FIO = name.title + name.first + name.last
        val address = items[position].location.street.number.toString() +
                        items[position].location.street.name
        val phoneNumber = items[position].phone

        Picasso.get().load(items[position].picture.medium).into(holder.imgPhoto)
        holder.txtFIO.text = FIO
        holder.txtAddress.text = address
        holder.txtPhoneNumber.text = phoneNumber

        holder.llUser.setOnClickListener{
            context.startActivity(Intent(context, ItemActivity::class.java))
        }
    }
}