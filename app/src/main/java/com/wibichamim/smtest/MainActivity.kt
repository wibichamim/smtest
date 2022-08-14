package com.wibichamim.smtest

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.wibichamim.smtest.databinding.ActivityMainBinding
import java.io.File
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var users : SharedPreferences

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        users = this.getSharedPreferences("Users", Context.MODE_PRIVATE)

        binding.imgProfile.setOnClickListener{
            getPermission()
        }

        binding.btnCheck.setOnClickListener{
            checkPalindrome()
        }

        binding.btnNext.setOnClickListener{
            validateInput()
        }
    }

    private fun validateInput() {
        var name = binding.valName.text.toString()

        if (imageUri == null) {
            Toast.makeText(this,"Choose your profile pic",Toast.LENGTH_SHORT).show()
        } else if (name.trim().isEmpty() || !checkPalindrome()) {
            Toast.makeText(this,"All input field must be valid",Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }

    }

    private fun checkPalindrome(): Boolean {
        val text : String = binding.valPalindrome.text.toString()
        val sb = StringBuilder(text)
        val reversed = sb.reverse()

        return if (text.trim().isNotEmpty()) {
            if (text.equals(reversed.toString(),true)) {
                Toast.makeText(this,"$text palindrome",Toast.LENGTH_SHORT).show()
                true
            } else {
                Toast.makeText(this,"$text Not palindrome",Toast.LENGTH_SHORT).show()
                false
            }
        } else {
            Toast.makeText(this,"Palindrome empty",Toast.LENGTH_SHORT).show()
            false
        }

    }

    private fun actionDialog() {
        val items = arrayOf("Camera", "Gallery")
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Get image from")
            setItems(items) { dialog, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            show()
        }
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun openCamera() {
        val capturedImage = File(externalCacheDir, "Profile_image.jpg")
        if(capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        imageUri = if(Build.VERSION.SDK_INT >= 24){
            FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",
                capturedImage)
        } else {
            Uri.fromFile(capturedImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraLauncher.launch(intent)
    }

    private var galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        imageUri = result
        val bitmap = BitmapFactory.decodeStream(
            imageUri?.let { contentResolver.openInputStream(it) })
        binding.imgProfile.setImageBitmap(bitmap)
    }

    private var cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = BitmapFactory.decodeStream(
                imageUri?.let { contentResolver.openInputStream(it) })
            binding.imgProfile.setImageBitmap(bitmap)
        }
    }

    private fun getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, 121)
            } else {
                actionDialog()
            }
        }
    }
}