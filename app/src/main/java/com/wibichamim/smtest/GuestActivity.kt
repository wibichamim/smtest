package com.wibichamim.smtest

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wibichamim.smtest.adapter.GuestAdapter
import com.wibichamim.smtest.data.Guest
import com.wibichamim.smtest.data.ResultGuest
import com.wibichamim.smtest.databinding.ActivityGuestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuestBinding

    lateinit var guestAdapter: GuestAdapter
    lateinit var recyclerView: RecyclerView
    var page : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Guest"

        recyclerView = binding.listGuest
        guestAdapter = GuestAdapter(this)
        recyclerView.adapter = guestAdapter

        fetchGuest()

        binding.refresh.setOnRefreshListener {
            fetchGuest()
        }

        recyclerView.addOnScrollListener(PaginationScrollListener())

    }

    private fun fetchGuest() {
        ApiInterface.create().getGuest(page).clone().enqueue(object : Callback<ResultGuest?> {
            override fun onResponse(call: Call<ResultGuest?>, response: Response<ResultGuest?>) {
                binding.refresh.isRefreshing = false
                if (response.body() != null) {

                    guestAdapter.addAll(response.body()!!.data)
                    guestAdapter.notifyDataSetChanged()

                    for (guest in response.body()!!.data) {
                        Log.d("Respo", "onResponse: " + guest.firstName)
                    }
                }
            }

            override fun onFailure(call: Call<ResultGuest?>, t: Throwable) {
                binding.refresh.isRefreshing = false
                Log.d("Respo", "onResponse: $t")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }
}
