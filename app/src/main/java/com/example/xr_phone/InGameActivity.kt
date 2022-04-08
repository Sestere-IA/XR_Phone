package com.example.xr_phone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.*

class InGameActivity : AppCompatActivity() {
    private var userIsInteracting : Boolean = false
    var playerName : TextView? = null
    var gameplayType : TextView? = null
    var pawnType : TextView? = null
    var equipe : TextView? = null
    var numExposition : TextView? = null
    var bTeamLeader = null //todo
    var flagAdvensement = null //todo
    var Speed: TextView? = null
    var distanceExp : TextView? = null
    var distancePK : TextView? = null


    override fun onUserInteraction() {
        super.onUserInteraction()
        userIsInteracting = true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_game)

        Wut.Client?.onUpdateLayoutFinishGame = this::onUpdateLayoutFinishGame
        Wut.Client?.onUpdateLayoutLobbyAfterGame = this::onUpdateLayoutLobbyAfterGame

        var commandtosend = ""
        var commingFromSelectScenario = false

        val layout = findViewById<LinearLayout>(R.id.primary_layout)
        val stopGame = findViewById<Button>(R.id.stopGame)
        val resetPlayer = findViewById<ImageButton>(R.id.resetplayer)
        val returnToParking = findViewById<ImageButton>(R.id.returntoParking)
        val lockmvt = findViewById<ImageButton>(R.id.lockmvt)
        val abort = findViewById<ImageButton>(R.id.abort)
        val arrow = findViewById<ImageButton>(R.id.arrowbtn1)
        val moreOptionPlayer = findViewById<TableRow>(R.id.more_obtion_player)
        this.playerName = findViewById<TextView>(R.id.labelPlayerName)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar) //todo
        val spinnerSurfaceCondition = findViewById<Spinner>(R.id.spinnerSurfaceCondition)
        val adpSurfaceCondition : ArrayAdapter<ESurfaceCondition> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ESurfaceCondition.values())
        spinnerSurfaceCondition.adapter = adpSurfaceCondition
        val spinnerWeather = findViewById<Spinner>(R.id.spinnerWeather)
        val spinnerTimeOfDay = findViewById<Spinner>(R.id.spinnerTimeofDay)
        val spinnerAdvertising = findViewById<Spinner>(R.id.spinnerAdvertising)
        val adpSpinnerTimeOfday : ArrayAdapter<ETimeOfDay> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ETimeOfDay.values())
        spinnerTimeOfDay.adapter = adpSpinnerTimeOfday
        val adpSpinnerWeather : ArrayAdapter<EWeather> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, EWeather.values())
        spinnerWeather.adapter = adpSpinnerWeather
        val adpSpinnerAdvertising : ArrayAdapter<EAdvertising> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, EAdvertising.values())
        spinnerAdvertising.adapter = adpSpinnerAdvertising

        arrow.setOnClickListener{
            if (moreOptionPlayer.visibility == View.VISIBLE) {
                moreOptionPlayer.visibility = View.GONE
                arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                moreOptionPlayer.visibility = View.VISIBLE
                arrow.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }

        stopGame.setOnClickListener {
            disableEnableControls(false, vg = layout)
            progressBar.visibility = View.VISIBLE
            Wut.Client?.changementLayout()
            Wut.Client?.queueCommand("exec returntolobby")
        }

        resetPlayer.setOnClickListener {
            Wut.Client?.queueCommand("exec playerreset alpha")
            //If 1 player change layout to lobby
        }

        returnToParking.setOnClickListener {
            Wut.Client?.queueCommand("exec PlayerReturnToParking alpha")
        }

        lockmvt.setOnClickListener {
            Wut.Client?.queueCommand("exec PlayerLockMovement alpha")
        }

        abort.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            Wut.Client?.queueCommand("exec PlayerAbort alpha")
        }

        spinnerAdvertising.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario){
                    commandtosend = "set Advertising ${spinnerAdvertising?.getItemAtPosition(
                        spinnerAdvertising.selectedItemPosition)}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinnerSurfaceCondition.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario){
                    commandtosend = "set SurfaceCondition ${spinnerSurfaceCondition?.selectedItemPosition}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinnerWeather.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario){
                    commandtosend = "set Weather ${spinnerWeather?.selectedItemPosition}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinnerTimeOfDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario){
                    commandtosend = "set TimeOfDay ${spinnerTimeOfDay?.selectedItemPosition}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        getAllDatatoUpdateLayoutInGame()
    }
    fun onUpdateLayoutFinishGame(){
        runOnUiThread {
            val activity_transition_xroad = Intent(this, activity_transition_xroad::class.java)
            startActivity(activity_transition_xroad)
        }
    }

    fun onUpdateLayoutLobbyAfterGame(){
        runOnUiThread {
            val intentToLobbyActivity = Intent(this, LobbyActivity::class.java)
            startActivity(intentToLobbyActivity)
        }
    }
    private fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
        for (i in 0 until vg.childCount) {
            val child = vg.getChildAt(i)
            child.isEnabled = enable
            if (child is ViewGroup) {
                disableEnableControls(enable, child)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.in_game_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.devMode){
            val intentToDevModeActivity = Intent(this, DevModeActivity::class.java)
            Wut.Client?.devModeActivated()
            startActivity(intentToDevModeActivity)
        }
        return super.onOptionsItemSelected(item)
    }
    fun getAllDatatoUpdateLayoutInGame(){
        runOnUiThread{
            playerName?.text = "Testing"
        }
    }
}