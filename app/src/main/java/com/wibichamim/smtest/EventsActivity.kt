package com.wibichamim.smtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wibichamim.smtest.adapter.EventAdapter
import com.wibichamim.smtest.data.Event
import com.wibichamim.smtest.data.Guest
import com.wibichamim.smtest.databinding.ActivityEventsBinding
import com.wibichamim.smtest.databinding.ActivityHomeBinding

class EventsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Events"

        recyclerView = binding.listEvents
        recyclerView.layoutManager = LinearLayoutManager(this)
        eventAdapter = EventAdapter(this)
        recyclerView.adapter = eventAdapter

        addData()

    }

    private fun addData() {
        val listData = listOf(
            Event("https://img.freepik.com/premium-vector/your-only-limit-is-you-typography-lettering-design_562118-301.jpg?w=2000",
                "Title",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "15 Jan 2021",
                "9:00 AM",
                "110.2513332",
                "-7.369574"
                ),

            Event("https://img.freepik.com/premium-vector/your-only-limit-is-you-typography-lettering-design_562118-301.jpg?w=2000",
                "Title",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "15 Jan 2021",
                "9:00 AM",
                "110.2513332",
                "-7.369574"
            )
        )

        eventAdapter.addAll(listData)
        eventAdapter.notifyDataSetChanged()
    }
}