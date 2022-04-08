package com.example.xr_phone

import android.os.AsyncTask
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.channels.Channel
import org.json.JSONArray
import org.json.JSONObject
import java.io.PrintWriter
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*


object Wut {
    var Client : RemoteControlClient? = null
    var ScenarioData : ArrayList<XRoadScenario> = ArrayList()
    var ServerMessageDevMode : Queue<String> = LinkedList<String>()
    var ServeurJsonMessage : Queue<XRoadLobbyResponse> = LinkedList<XRoadLobbyResponse>()
    var ServerStartGame : Queue<String> = LinkedList<String>()
}

class RemoteControlClient: AsyncTask<Void, Void, String>() {
    var bStop : Boolean = false
    var changeLayout : Boolean = false
    var commandQueue: Queue<String> = LinkedList<String>()

    var onConnectionSuccessDelegate: (() -> Unit?)? = null
    var onConnectionFailedDelegate: (() -> Unit?)? = null
    var onStartGameNoSelectScenario: (() -> Unit?)? = null
    var onUpdateLayoutOnSelectScenario: (() -> Unit?)? = null
    var onUpdateLayoutOnStartTransitionOnGameSelected: (() -> Unit?)? = null
    var onUpdateLayoutOnGameSelected: (() -> Unit?)? = null
    var onUpdateLayoutFinishGame: (() -> Unit?)? = null
    var onUpdateLayoutLobbyAfterGame: (() -> Unit?)? = null
    var onConncetionError: ((/*Error*/) -> Unit?)? = null
    var onReceiveMessage:((msg: String) -> Unit?)? = null
    var bInnit = false

    var getIpPort = ""

    var devMode = false

    fun queueCommand(command: String){
        if (command == "EXIT") {
            bStop = true
        }
        else{
            commandQueue.add(command)
        }
        return
    }

    fun devModeActivated() {
        devMode = true
    }

    fun changementLayout(){
        changeLayout = true
    }

    override fun doInBackground(vararg params: Void?): String {

        try {
            //val ipin = "192.168.1.16" //TODO
            //val portin = 7778 //TODO
            val ipPort = getIpPort.split(":")
            val ipin = ipPort.elementAt(0)
            val portin = ipPort.elementAt(1).toInt()
            val client = Socket()
            bInnit = true
            println(ipin)
            println(portin)
            client.connect(InetSocketAddress(ipin,portin), 1000)
            val output = PrintWriter(client.getOutputStream(), true)
            Thread.sleep(1000)
            whileTesting(client, bInnit, output)
        } catch (ex:Exception) {
            onConnectionFailedDelegate?.invoke()
            return ""
        }

        return ""
    }

    fun whileTesting(client : Socket, bInnit : Boolean, output:PrintWriter){
        var testingIfisFirstText = bInnit
        while (true){

            var json: JSONObject = JSONObject()
            if (bStop) {
                client.close()
                break
            }
            if (client.getInputStream().available() > 0) {
                var message = ""
                while (client.getInputStream().available() > 0) {
                    val data = ByteArray(client.getInputStream().available())
                    client.getInputStream().read(data, 0, client.getInputStream().available())
                    message += data.decodeToString()
                }

                if (testingIfisFirstText) {
                    message = message.removePrefix("INIT\n")
                    println(message)
                    try {
                        json = JSONObject(message)
                        var arrayScenario: JSONArray = json.getJSONArray("ScenarioData")

                        var i: Int = -1
                        while (++i < arrayScenario.length()) {
                            var elem = arrayScenario[i]
                            var scenario = XRoadScenario.FromJson(elem as JSONObject)
                            Wut.ScenarioData.add(scenario)

                        }
                    } catch (e: org.json.JSONException) {

                    }
                    testingIfisFirstText = false
                    onConnectionSuccessDelegate?.invoke()
                } else //Le message n'est plus le init
                {

                    if (message != "") {
                        try {
                            if (message.isNotEmpty()) {
                                println("message = $message")
                                val header = message.substring(0, message.indexOf("\n"));
                                val body = message.substring(header.length)
                                if (header.length > 2)//message d'execution de la game
                                {
                                    Wut.ServerStartGame.add(message)
                                    when {
                                        "KO Cannot start game: Please select a scenario" in message -> {
                                            //Make a Toast to please sleect a scenario
                                            onStartGameNoSelectScenario?.invoke()
                                        }
                                        changeLayout -> {
                                            changeLayout = false
                                            onUpdateLayoutOnStartTransitionOnGameSelected?.invoke()
                                        }
                                        "MATCHSTATE InProgress" in message -> {
                                            onUpdateLayoutOnGameSelected?.invoke()
                                        }
                                        "MATCHSTATE WaitingPostMatch" in message -> {
                                            onUpdateLayoutFinishGame?.invoke()
                                        }
                                        "MATCHSTATE Lobby" in message -> {
                                            onUpdateLayoutLobbyAfterGame?.invoke()
                                        }
                                    }


                                }else{
                                    json = JSONObject(body)
                                    println("json is :")
                                    println(json)
                                    val DataMessage = XRoadLobbyResponse.FromJson(json)
                                    Wut.ServeurJsonMessage.add(DataMessage)
                                    onUpdateLayoutOnSelectScenario?.invoke()

                                    message = ""
                                }
                            }
                        } catch (e: org.json.JSONException) {
                        }
                    }
                }

                if (devMode) {

                    if (message != "") {
                        println("message ! $message")
                        while (Wut.ServerMessageDevMode.size > 0)
                            Wut.ServerMessageDevMode.remove()
                        Wut.ServerMessageDevMode.add(message)
                    }

                }
            }

            while (commandQueue.size > 0) {
                val command: String = commandQueue.remove()
                println(command)
                output.println(command)
            }

            Thread.sleep(1000)
        }
    }}