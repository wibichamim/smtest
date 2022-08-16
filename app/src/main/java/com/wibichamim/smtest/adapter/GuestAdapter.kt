package com.wibichamim.smtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wibichamim.smtest.R
import com.wibichamim.smtest.data.Guest

class GuestAdapter(val context: Context, private val onClickListener: OnClickListener) : RecyclerView.Adapter<GuestAdapter.MyViewHolder> () {

    var guestList : MutableList<Guest> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guest,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = guestList[position].firstName
        Glide.with(context).load(guestList[position].avatar)
            .apply (RequestOptions().centerCrop())
            .into(holder.profilePic)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(guestList[position])
        }
    }

    override fun getItemCount(): Int {
        return guestList.size
    }

    class MyViewHolder(item: View?) : RecyclerView.ViewHolder(item!!) {
        val profilePic : ImageView = item!!.findViewById(R.id.img_profile)
        val name : TextView = item!!.findViewById(R.id.tv_name)
    }

    fun addAll(moveResult : List<Guest>) {
        for (result in moveResult) {
            add(result)
        }
    }

    fun add(guest: Guest) {
        guestList.add(guest)
    }

    fun getGuest(position: Int): Guest {
        return guestList[position]
    }

    class OnClickListener(val clickListener: (guest : Guest) -> Unit) {
        fun onClick(guest: Guest) = clickListener(guest)
    }

}