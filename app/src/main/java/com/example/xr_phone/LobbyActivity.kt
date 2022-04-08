package com.example.xr_phone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import org.json.JSONObject
import java.util.*
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo


class LobbyActivity : AppCompatActivity() {
    var primaryLayout : LinearLayout? = null
    var tableLayoutScenario : TableLayout? = null
    var spinnerSequence : Spinner? = null
    var spinnerScenario : Spinner? = null
    var commingFromSelectScenario = false
    var scenarioSelected = false
    var tempShort = ""
    var describeScenario : TextView? = null
    var lockMvtPlayer : CheckBox? = null
    var mute : CheckBox? = null
    var wordSeed : TextView? = null
    var spinnerTimeOfDay : Spinner? = null
    var spinnerWeather : Spinner? = null
    var checkboxShortScenario : CheckBox? = null
    var spinnerSurfaceCondition : Spinner? = null
    var spinnerAIDensity : Spinner? = null
    var spinnerIAGenerationMethod : Spinner? = null
    var aISeed : TextView? = null
    var spinnerRotation : Spinner? = null
    var checkboxReplayMatrix : CheckBox? = null
    var checkboxMetric : CheckBox? = null
    var checkboxEyeTracking : CheckBox? = null
    var rowParticipant1 : TableRow? = null
    var rowParticipant2 : TableRow? = null
    var rowParticipant3 : TableRow? = null
    var rowParticipant4 : TableRow? = null
    var rowParticipant5 : TableRow? = null
    var listOfParticipant : MutableList<TableRow?> = mutableListOf(rowParticipant1,
        rowParticipant2, rowParticipant3, rowParticipant4, rowParticipant5)
    var adpSpinnerGameplayType : ArrayAdapter<String>? = null
    var adpSpinnerTeam : ArrayAdapter<Any>? = null
    var adpSpinnerPawnType : ArrayAdapter<Any>? = null

    var progressBar : ProgressBar? = null

    var spinnerAdvertising : Spinner? = null

    var startGame : Button? = null

    private var userIsInteracting : Boolean = false
    override fun onUserInteraction() {
        super.onUserInteraction()
        userIsInteracting = true
    }
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        Wut.Client?.onUpdateLayoutOnSelectScenario = this::updataLayoutOnSelectScenario
        Wut.Client?.onUpdateLayoutOnStartTransitionOnGameSelected = this::updateLayoutTransitionOnRunXRoad
        Wut.Client?.onUpdateLayoutOnGameSelected = this::updateLayoutInGameXRoad
        Wut.Client?.onStartGameNoSelectScenario = this::onStartGameNoSelectScenario

        val sequences: ArrayList<String> = ArrayList()

        for (it in Wut.ScenarioData)
        {
            if (!sequences.contains(it.category)) {
                sequences.add(it.category)
            }
                }

        sequences.sort()

        sequences.add(0, "Séquence")

        var commandtosend = ""
        var bOnCreate = true
        scenarioSelected = false
        commingFromSelectScenario = false

        this.tableLayoutScenario = findViewById<TableLayout>(R.id.TableLayoutsccenario)
        this.primaryLayout = findViewById<LinearLayout>(R.id.primary_layout)
        this.spinnerSequence = findViewById<Spinner>(R.id.spinner_select_sequence)
        this.spinnerScenario = findViewById<Spinner>(R.id.spinner_select_scenario)
        val arrow1 = findViewById<ImageButton>(R.id.arrowbtn1)
        val arrow2 = findViewById<ImageButton>(R.id.arrow_button2)
        val viewTable2 = findViewById<TableLayout>(R.id.more_obtion_1)
        val viewTable1 = findViewById<TableLayout>(R.id.more_obtion_0)
        this.describeScenario = findViewById<TextView>(R.id.labeldescribe_scenario)
        val labelScenario = findViewById<TextView>(R.id.labelscenario)
        this.mute = findViewById<CheckBox>(R.id.checkBox_Mute)
        this.lockMvtPlayer = findViewById<CheckBox>(R.id.checkBox_lock_mvt)
        this.wordSeed = findViewById<TextView>(R.id.number_word_seed)
        this.spinnerTimeOfDay = findViewById<Spinner>(R.id.spinnerTimeofDay)
        this.spinnerWeather = findViewById<Spinner>(R.id.spinnerWeather)
        this.checkboxShortScenario = findViewById<CheckBox>(R.id.checkBoxScenarioShort)
        tempShort = ""
        this.spinnerSurfaceCondition = findViewById<Spinner>(R.id.spinnerSurfaceCondition)
        this.spinnerIAGenerationMethod = findViewById<Spinner>(R.id.spinner_IA_generation)
        this.spinnerAIDensity = findViewById<Spinner>(R.id.spinner_IA_density)
        this.aISeed = findViewById<TextView>(R.id.editNumber_IA_seed)
        this.spinnerAdvertising = findViewById<Spinner>(R.id.spinnerAdvertising)
        this.spinnerRotation = findViewById<Spinner>(R.id.spinnerRotation)
        val listOfRotation = arrayOf(0,1,2,3,4)
        this.checkboxReplayMatrix = findViewById<CheckBox>(R.id.checkBox_replay_matrix)
        this.checkboxMetric = findViewById<CheckBox>(R.id.checkBox_metric)
        this.checkboxEyeTracking = findViewById<CheckBox>(R.id.checkBox_eye_tracking)
        this.rowParticipant1 = findViewById<TableRow>(R.id.rowParticipant1)
        this.rowParticipant2 = findViewById<TableRow>(R.id.rowParticipant2)
        this.rowParticipant3 = findViewById<TableRow>(R.id.rowParticipant3)
        this.rowParticipant4 = findViewById<TableRow>(R.id.rowParticipant4)
        this.rowParticipant5 = findViewById<TableRow>(R.id.rowParticipant5)
        this.listOfParticipant = mutableListOf(this.rowParticipant1,
            this.rowParticipant2, this.rowParticipant3, this.rowParticipant4, this.rowParticipant5)
        val listOfGameplayType = EGamePlayType.values().contentToString().replace(Regex("^.|.$"), "").split(", ").toTypedArray()
        val listOfPawnType = EPawnType.values().contentToString().replace(Regex("^.|.$"), "").split(", ").toTypedArray()

        val listOfTeam = arrayOf("-", 1, 2, 3, 4)

        this.progressBar = findViewById<ProgressBar>(R.id.progressBar)
        this.startGame = findViewById<Button>(R.id.go)


        val spinnerAdapterSequence= object : ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, sequences) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup,
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if(position == 0) {
                    view.setTextColor(Color.GRAY)
                }
                return view
            }

        }

        spinnerAdapterSequence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.spinnerSequence?.adapter = spinnerAdapterSequence

        val spinnerAdapterScenario= object : ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup,
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if(position == 0) {
                    view.setTextColor(Color.GRAY)
                }
                return view
            }

        }

        spinnerAdapterScenario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.spinnerScenario?.adapter = spinnerAdapterScenario

        val adpSpinnerTimeOfday : ArrayAdapter<ETimeOfDay> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ETimeOfDay.values())
        this.spinnerTimeOfDay?.adapter = adpSpinnerTimeOfday

        val adpSpinnerWeather : ArrayAdapter<EWeather> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, EWeather.values())
        this.spinnerWeather?.adapter = adpSpinnerWeather

        val adpSurfaceCondition : ArrayAdapter<ESurfaceCondition> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ESurfaceCondition.values())
        this.spinnerSurfaceCondition?.adapter = adpSurfaceCondition

        val adpSpinnerIAGenerationMethod : ArrayAdapter<EAIGenerationMethod> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, EAIGenerationMethod.values())
        this.spinnerIAGenerationMethod?.adapter = adpSpinnerIAGenerationMethod

        val adpSpinnerIADensity : ArrayAdapter<EAIDensity> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, EAIDensity.values())
        this.spinnerAIDensity?.adapter = adpSpinnerIADensity

        val adpSpinnerAdvertising : ArrayAdapter<EAdvertising> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, EAdvertising.values())
        this.spinnerAdvertising?.adapter = adpSpinnerAdvertising

        val adpSpinnerRotation : ArrayAdapter<Int> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOfRotation)
        this.spinnerRotation?.adapter = adpSpinnerRotation

        this.adpSpinnerGameplayType = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOfGameplayType)

        this.adpSpinnerTeam = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOfTeam)

        this.adpSpinnerPawnType = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOfPawnType)


        arrow2.setOnClickListener{
            if (viewTable2.visibility == View.VISIBLE) {
                viewTable2.visibility = View.GONE
                arrow2.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                viewTable2.visibility = View.VISIBLE
                arrow2.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }

        arrow1.setOnClickListener{
            if (viewTable1.visibility == View.VISIBLE) {
                viewTable1.visibility = View.GONE
                arrow1.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                viewTable1.visibility = View.VISIBLE
                arrow1.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }

        labelScenario.setOnClickListener{
            if (this.describeScenario!!.text == ""){
                //Ne rien faire
            } else{
                if (this.describeScenario!!.visibility == View.VISIBLE){
                    this.describeScenario!!.visibility = View.GONE
                } else {
                    this.describeScenario!!.visibility = View.VISIBLE
                }
            }

    }

        this.spinnerSequence?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                val selectedCategory = parent.getItemAtPosition(position)

                describeScenario!!.visibility = View.GONE

                val displayedScenarioName: ArrayList<String> = ArrayList()
                for (it in Wut.ScenarioData){
                    if (it.category == selectedCategory) {
                        displayedScenarioName.add(it.name)
                    }

                }
                displayedScenarioName.sort()
                displayedScenarioName.add(0, "Scénario")
                spinnerAdapterScenario.clear()
                spinnerAdapterScenario.addAll(displayedScenarioName)
                spinnerScenario!!.setSelection(spinnerAdapterScenario.getPosition("Scénario"))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        this.spinnerScenario!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                val selectedScenario = parent.getItemAtPosition(position)
                var valueCommand = ""
                val basicCommand = "SET SCENARIO"

                if(bOnCreate) {
                    bOnCreate = false
                }
                else if (spinnerScenario!!.selectedItemPosition == 0) {
                    disableEnableControls(false, vg = primaryLayout!!)
                    disableEnableControls(true, vg = tableLayoutScenario!!)
                }
                else {
                    progressBar!!.visibility = View.VISIBLE
                    disableEnableControls(false, vg = primaryLayout!!)
                }


                for (it in Wut.ScenarioData){
                    if (it.name == selectedScenario) {
                        scenarioSelected = true
                        valueCommand = it.uniqueTag
                        val command = "$basicCommand $valueCommand"
                        Wut.Client?.queueCommand(command)

            }}
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        this.checkboxShortScenario?.setOnClickListener{
            Wut.Client?.queueCommand("set bHasShortVersion ${checkboxShortScenario?.isChecked}")
            println(commandtosend)
            Wut.Client?.queueCommand(commandtosend)

        }

        this.spinnerRotation?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario and scenarioSelected){
                    commandtosend = "set SpawnRotationOffset ${spinnerRotation?.getItemAtPosition(spinnerRotation!!.selectedItemPosition)}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
                }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        this.mute?.setOnClickListener{
            Wut.Client?.queueCommand("set bNoSound ${mute!!.isChecked}")
            println(commandtosend)
            Wut.Client?.queueCommand(commandtosend)
        }

        this.lockMvtPlayer?.setOnClickListener{
            Wut.Client?.queueCommand("set bLockPlayerMovement ${lockMvtPlayer!!.isChecked}")
            println(commandtosend)
            Wut.Client?.queueCommand(commandtosend)
        }

        this.spinnerSurfaceCondition?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario and scenarioSelected){
                    commandtosend = "set SurfaceCondition ${spinnerSurfaceCondition?.selectedItemPosition}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        this.spinnerWeather?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario and scenarioSelected){
                    commandtosend = "set Weather ${spinnerWeather?.selectedItemPosition}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        this.spinnerTimeOfDay?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario and scenarioSelected){
                    commandtosend = "set TimeOfDay ${spinnerTimeOfDay?.selectedItemPosition}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        this.spinnerAdvertising?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario and scenarioSelected){
                    commandtosend = "set Advertising ${spinnerAdvertising?.getItemAtPosition(
                        spinnerAdvertising!!.selectedItemPosition)}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        this.spinnerAIDensity?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario and scenarioSelected){
                    commandtosend = "set AIDensity ${spinnerAIDensity?.selectedItemPosition}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        this.spinnerIAGenerationMethod?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            )
            {
                if (userIsInteracting and !commingFromSelectScenario and scenarioSelected){
                    commandtosend = "set AIGenerationMethod ${spinnerIAGenerationMethod?.selectedItemPosition}"
                    println(commandtosend)
                    Wut.Client?.queueCommand(commandtosend)
                }
                commingFromSelectScenario = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        this.aISeed?.setOnEditorActionListener{ v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                commandtosend = "set AISeed ${aISeed?.text}"
                println(commandtosend)
                Wut.Client?.queueCommand(commandtosend)
                true
            } else {
                false
            }
    }

        this.wordSeed?.setOnEditorActionListener{ v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                commandtosend = "set WorldSeed ${this.wordSeed?.text}"
                println(commandtosend)
                Wut.Client?.queueCommand(commandtosend)
                true
            } else {
                false
            }
        }

        this.checkboxReplayMatrix?.setOnClickListener{
            Wut.Client?.queueCommand("set bMatrixReplayEnabled ${checkboxReplayMatrix?.isChecked}")
            println(commandtosend)
            Wut.Client?.queueCommand(commandtosend)
        }

        this.checkboxEyeTracking?.setOnClickListener{
            Wut.Client?.queueCommand("set bEyeTrackingEnabled ${checkboxEyeTracking?.isChecked}")
            println(commandtosend)
            Wut.Client?.queueCommand(commandtosend)
        }

        this.checkboxMetric?.setOnClickListener{
            Wut.Client?.queueCommand("set bMetricsEnabled ${checkboxMetric?.isChecked}")
            println(commandtosend)
            Wut.Client?.queueCommand(commandtosend)
        }

        this.startGame?.setOnClickListener{
            disableEnableControls(false, vg = this.primaryLayout!!)
            progressBar!!.visibility = View.VISIBLE
            Wut.ServerStartGame.clear()
            Wut.Client?.changementLayout()
            Wut.Client?.queueCommand("exec startgame")
            //layout chargement of game
            //Changement de layout
            //if KO Please select a scenario TOAST

        }
        disableEnableControls(false, vg = primaryLayout!!)
        disableEnableControls(true, vg = tableLayoutScenario!!)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_disconnect){
            // afficher une fenetre de confirmation
            showLogoutConfirmDialog()

        }
        if(item.itemId == R.id.devMode){
            val intentToDevModeActivity = Intent(this, DevModeActivity::class.java)
            Wut.Client?.devModeActivated()
            startActivity(intentToDevModeActivity)
        }

        if(item.itemId == R.id.LastReplay){
            Wut.Client?.queueCommand("exec lastreplay")
        }
        if(item.itemId == R.id.EndGame){
            Wut.Client?.queueCommand("exec endgame")
        }
        if(item.itemId == R.id.ListRepaly){
            //Todo faire le layout avec la list
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLogoutConfirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation de déconnexion")
        builder.setMessage("Etres-vous sure de vouloir vous déconnecter de la session ?")
        builder.setPositiveButton("Se déconnecter") { _, _ ->
            Wut.ScenarioData.clear()
            Wut.Client?.queueCommand("EXIT")
            val intentToReturnToConnectActivity = Intent(this, ConnectActivity::class.java)
            startActivity(intentToReturnToConnectActivity)
        }

        builder.setNegativeButton("Non") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        builder.setNeutralButton("Annuler"){ dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val aldertDialog: AlertDialog = builder.create()
        aldertDialog.show()
    }

    fun updataLayoutOnSelectScenario() {
        runOnUiThread {
            val selectedScenario = this.spinnerScenario?.selectedItem
            print("selectedScenario = $selectedScenario")
            var valueCommand = ""
            val basicCommand = "SET SCENARIO"
            this.commingFromSelectScenario = true


            for (it in Wut.ScenarioData) {
                if (it.name == selectedScenario) {
                    this.scenarioSelected = true

                    if (it.expectedTime != 0) {
                        this.tempShort =
                            "\nTemps Raccourcis :" + (it.expectedTimeShort.toFloat() / 60).toString()
                    }

                    val describe: String =
                        it.description + "\nTemps: " +
                                (it.expectedTime.toFloat() / 60).toString() + tempShort + " min\nMap: " +
                                it.map
                    this.describeScenario!!.text = describe


                    this.mute?.isChecked = it.bNoSound
                    this.lockMvtPlayer?.isChecked = it.bLockPlayerMovementToPath
                    this.wordSeed?.text = (it.worldSeed).toString()
                    this.spinnerTimeOfDay?.setSelection((it.timeOfDay.ordinal))
                    this.spinnerWeather?.setSelection(it.weather.ordinal)
                    this.checkboxShortScenario?.isEnabled = it.bHasShortVersion
                    this.spinnerSurfaceCondition?.setSelection(it.surfaceCondition.ordinal)
                    this.spinnerAIDensity?.setSelection(it.aIdensity.ordinal)
                    this.spinnerIAGenerationMethod?.setSelection(it.aIGenerationMethod.ordinal)
                    this.aISeed?.text = (it.aISeed).toString()
                }
            }

            while (Wut.ServeurJsonMessage.size > 0) {
                for (it in Wut.ServeurJsonMessage) {
                    this.mute?.isChecked = it.bNoSound
                    this.lockMvtPlayer?.isChecked = it.bLockPlayerMovementToPath
                    this.spinnerRotation?.setSelection(it.spawnRotation)
                    //spinnerAdvertising.setSelection(it.advertising.ordinal)
                    this.spinnerTimeOfDay?.setSelection((it.timeOfDay.ordinal))
                    this.spinnerWeather?.setSelection(it.weather.ordinal)
                    this.spinnerSurfaceCondition?.setSelection(it.surfaceCondition.ordinal)
                    this.wordSeed?.text = (it.worldSeed).toString()
                    this.spinnerIAGenerationMethod?.setSelection(it.aIGenerationMethod.ordinal)
                    this.spinnerAIDensity?.setSelection(it.aIdensity.ordinal)
                    this.aISeed?.text = (it.aISeed).toString()
                    this.checkboxReplayMatrix?.isChecked = it.bMatrixReplayEnabled
                    this.checkboxMetric?.isChecked = it.bMetricsEnabled
                    this.checkboxEyeTracking?.isChecked = it.bEyeTrackingEnabled
                    for (playerNumber in 0 until it.PlayerData.length()) {
                        listOfParticipant[playerNumber]?.visibility = View.VISIBLE
                        val playerData: JSONObject = it.PlayerData.getJSONObject(playerNumber)
                        val playername: String = playerData.getString("PlayerName")
                        val gameplayType: Int = playerData.getInt("GameplayType")
                        val pawnType = EPawnType.values()[playerData.getInt("PawnType") + 1]
                        val team: Int = playerData.getInt("Team")
                        var bTeamLeader: Boolean = playerData.getBoolean("bTeamLeader")
                        // TODO
                        val labelPlayer: TextView =
                            listOfParticipant[playerNumber]?.getChildAt(0) as TextView
                        labelPlayer.text = playername

                        val spinnerGameplayType: Spinner =
                            listOfParticipant[playerNumber]?.getChildAt(1) as Spinner
                        spinnerGameplayType.adapter = this.adpSpinnerGameplayType
                        spinnerGameplayType.setSelection(gameplayType)

                        val spinnerPawnType: Spinner =
                            listOfParticipant[playerNumber]?.getChildAt(2) as Spinner
                        spinnerPawnType.adapter = adpSpinnerPawnType
                        spinnerPawnType.setSelection(playerData.getInt("PawnType"))

                        val spinerTeam: Spinner =
                            listOfParticipant[playerNumber]?.getChildAt(3) as Spinner
                        spinerTeam.adapter = adpSpinnerTeam
                        spinerTeam.setSelection(team)

                        val btnCrown: ImageButton =
                            listOfParticipant[playerNumber]?.getChildAt(4) as ImageButton
                        if (team != 0) {
                            btnCrown.isEnabled = true
                            btnCrown.setColorFilter(R.color.grey_xroad)

                        } else {
                            btnCrown.isEnabled = false
                            btnCrown.setColorFilter(android.R.color.white)
                        }

                    }

                }

                Wut.ServeurJsonMessage.remove()
            }
            commingFromSelectScenario = false
            progressBar!!.visibility = View.GONE
            disableEnableControls(true, this.primaryLayout!!)
        }
    }

    fun updateLayoutTransitionOnRunXRoad(){
        runOnUiThread {
            disableEnableControls(true, this.primaryLayout!!)
            progressBar!!.visibility = View.GONE
            println("Lancement de la game")
            println("Server StartGame = ${Wut.ServerStartGame}")
            if ("KO Cannot start game: Please select a scenario" in Wut.ServerStartGame.element()){
                println("Please select a Scenario")
                Wut.ServerStartGame.clear()
            } else{
                Wut.ServerStartGame.clear()
                val activity_transition_xroad = Intent(this, activity_transition_xroad::class.java)
                startActivity(activity_transition_xroad)
            } }
    }

    fun updateLayoutInGameXRoad(){
        runOnUiThread {
                Wut.ServerStartGame.clear()
                val intentToInGameActivity = Intent(this, InGameActivity::class.java)
                startActivity(intentToInGameActivity)
            } }

    fun onStartGameNoSelectScenario()
    {
        this.runOnUiThread {
            disableEnableControls(true, this.primaryLayout!!)
            this.startGame?.isEnabled = true
            progressBar?.visibility = View.GONE
            Toast.makeText(this, "KO Cannot start game: Please select a scenario", Toast.LENGTH_SHORT).show();
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
    }
