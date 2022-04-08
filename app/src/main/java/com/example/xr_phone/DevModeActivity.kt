package com.example.xr_phone
//caca
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class DevModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_mode)

        val enterCommand = findViewById<EditText>(R.id.commandToEnter)
        val sendCommand = findViewById<Button>(R.id.sendCommand)
        val prompt = findViewById<TextView>(R.id.serverResponse)

        prompt.movementMethod = ScrollingMovementMethod()
        prompt.text = ""

        var serverMessage = ""

        sendCommand.setOnClickListener{
            if(enterCommand.text.toString() != "") {
                prompt.text = ""
                Wut.Client?.queueCommand(enterCommand.text.toString())
                Thread.sleep(1000)

                serverMessage += "Command send: " +
                        enterCommand.text.toString() + "\n" + "Server Response : " +
                        Wut.ServerMessageDevMode + "\n\n"

                prompt.text = serverMessage
                enterCommand.setText("")
                var scrollAmount = prompt.layout.getLineTop(prompt.lineCount) - prompt.height
                if(scrollAmount > 0){
                    prompt.scrollTo(0, scrollAmount)
                }else{
                    prompt.scrollTo(0,0)
                }
            }
        }
    }
}