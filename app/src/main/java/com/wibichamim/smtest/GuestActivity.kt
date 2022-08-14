package com.wibichamim.smtest

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wibichamim.smtest.databinding.ActivityGuestBinding
import com.wibichamim.smtest.model.Guest
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.log

class GuestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuestBinding
    private val apiInterface = ApiInterface.create().getGuest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Guest"

        fetchGuest()

    }

    private fun fetchGuest() {
        apiInterface.clone().enqueue(object : retrofit2.Callback<List<Guest>?> {
            override fun onResponse(call: Call<List<Guest>?>, response: Response<List<Guest>?>) {
                Log.d("Respo", "onResponse: " + response.body())
                if (response.body() != null) {
                    Log.d("Respo", "onResponse: " + response.body())
                }
            }

            override fun onFailure(call: Call<List<Guest>?>, t: Throwable) {
                Toast.makeText(this@GuestActivity,t.toString(),Toast.LENGTH_LONG).show()
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
