package com.wibichamim.smtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            startActivity(intent)
        }
    }
}