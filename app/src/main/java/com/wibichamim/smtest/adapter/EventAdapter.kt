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
import com.wibichamim.smtest.data.Event

class EventAdapter(val context: Context) : RecyclerView.Adapter<EventAdapter.MyViewHolder> () {

    var eventList : MutableList<Event> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = eventList[position].title
        holder.content.text = eventList[position].content
        holder.date.text = eventList[position].date
        holder.time.text = eventList[position].time
        Glide.with(context).load(eventList[position].image)
            .apply (RequestOptions().centerCrop())
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class MyViewHolder(item: View?) : RecyclerView.ViewHolder(item!!) {
        val image : ImageView = item!!.findViewById(R.id.img_event)
        val title : TextView = item!!.findViewById(R.id.title)
        val content : TextView = item!!.findViewById(R.id.content)
        val date : TextView = item!!.findViewById(R.id.date)
        val time : TextView = item!!.findViewById(R.id.time)
    }

    fun addAll(moveResult : List<Event>) {
        for (result in moveResult) {
            add(result)
        }
    }

    fun add(event: Event) {
        eventList.add(event)
    }

    fun getGuest(position: Int): Event {
        return eventList[position]
    }

}