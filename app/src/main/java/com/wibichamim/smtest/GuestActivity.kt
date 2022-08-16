package com.wibichamim.smtest

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
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
    private lateinit var recyclerView: RecyclerView
    private var page : Int = 1
    private var totalPage: Int = 3

    private var isLoading = false

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
        guestAdapter = GuestAdapter(this, GuestAdapter.OnClickListener {guest ->
            returnData(guest)
        })
        recyclerView.adapter = guestAdapter

        fetchGuest()

        binding.refresh.setOnRefreshListener {
            guestAdapter.guestList.clear()
            page = 1
            fetchGuest()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager

                val visibleItemCount = layoutManager.itemCount
                val pastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val isLastPosition = visibleItemCount.minus(1) == pastVisibleItem

                Toast.makeText(this@GuestActivity,isLoading.toString(),Toast.LENGTH_LONG).show()
                if (!isLoading && isLastPosition && page < totalPage){
                    Toast.makeText(this@GuestActivity,"here",Toast.LENGTH_LONG).show()
                    page++
                    fetchGuest()
                }
            }
        })

    }

    private fun returnData(guest: Guest) {
        var data = Intent()
        var name = guest.firstName

        data.data = Uri.parse(name)
        setResult(RESULT_OK,data)
        finish()
    }

    private fun fetchGuest() {
        isLoading = true
        Toast.makeText(this,page.toString(),Toast.LENGTH_SHORT).show()
        ApiInterface.create().getGuest(page).clone().enqueue(object : Callback<ResultGuest?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<ResultGuest?>, response: Response<ResultGuest?>) {
                binding.refresh.isRefreshing = false
                isLoading = false
                if (response.body() != null) {

                    guestAdapter.addAll(response.body()!!.data)
                    guestAdapter.notifyDataSetChanged()
                } else {
                    Log.d("Response", "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<ResultGuest?>, t: Throwable) {
                binding.refresh.isRefreshing = false
                isLoading = false
                Log.d("Response", "onResponse: $t")
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
