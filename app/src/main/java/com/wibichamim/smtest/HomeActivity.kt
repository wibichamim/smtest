package com.wibichamim.smtest

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.wibichamim.smtest.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var username = intent.getStringExtra("name")

        binding.username.text = username

        binding.btnGuest.setOnClickListener{
            val intent = Intent(this,GuestActivity::class.java)
            guestLauncher.launch(intent)
        }

        binding.btnEvent.setOnClickListener{
            startActivity(Intent(this,EventsActivity::class.java))
        }
    }

    private var guestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val res : String? = result.data?.data.toString()
            binding.btnGuest.text = res
        }
    }
}