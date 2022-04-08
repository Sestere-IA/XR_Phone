package com.example.xr_phone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.net.InetSocketAddress
import java.util.*


class ConnectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_connect_menu)

        val connect = findViewById<Button>(R.id.connect)
        val identifiant = findViewById<EditText>(R.id.identifiant)
        val command = ""

        connect.setOnClickListener{println("Click on Button")}
        connect.setOnClickListener{

            //var txtIdentifiant = identifiant.text.toString()

            //val ipAddress = txtIdentifiant.substringBefore(":")
            //val port = txtIdentifiant.substringAfter(":").toInt()

            Wut.Client = RemoteControlClient()
            Wut.Client?.onConnectionSuccessDelegate = this::onConnectionSuccess
            Wut.Client?.onConnectionFailedDelegate = this::onConnectionFailed

            Wut.Client?.getIpPort = identifiant.text.toString()
            Wut.Client?.execute()

        }
    }

    fun onConnectionSuccess()
    {
        val intentToConnectMenuActivity = Intent(this, LobbyActivity::class.java)
        startActivity(intentToConnectMenuActivity)
    }

    fun onConnectionFailed()
    {
        this.runOnUiThread {
            Toast.makeText(this, "L'addresse ip que vous essayer d'entrer n'est pas valide", Toast.LENGTH_SHORT).show();
        }
    }


}
