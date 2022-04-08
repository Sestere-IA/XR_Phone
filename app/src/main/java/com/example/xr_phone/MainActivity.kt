package com.example.xr_phone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import java.net.Socket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgBtn = findViewById<ImageButton>(R.id.imageButton)
        imgBtn.setOnClickListener{
            Log.d("XR_App", "Click on Button")
            val intentToConnectMenuActivity = Intent(this, ConnectActivity::class.java)
            startActivity(intentToConnectMenuActivity)
                }
            }



        }