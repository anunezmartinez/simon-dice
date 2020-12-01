package com.simongame

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var state: Boolean

        //Button declaration
        val buttonRed: Button = findViewById(R.id.red)
        val buttonYellow: Button = findViewById(R.id.yellow)
        val buttonBlue: Button = findViewById(R.id.blue)
        val buttonGreen: Button = findViewById(R.id.green)
        val buttonStart: Button = findViewById(R.id.start)
        val buttonCheck: Button = findViewById(R.id.check)

        //List of buttons
        val btnColor = listOf(buttonRed, buttonGreen, buttonYellow, buttonBlue)

        //Toat declaration
        val toast0 = Toast.makeText(applicationContext, applicationContext.getString(R.string.endStatus), Toast.LENGTH_SHORT)
        val toast1 = Toast.makeText(applicationContext, applicationContext.getString(R.string.startStatus), Toast.LENGTH_SHORT)


        val gameModel by viewModels<LiveData>()

        gameModel.gameState.observe(this, Observer { gs ->
            state = gs
            if (state) {
                buttonRed.isEnabled = false
                buttonYellow.isEnabled = false
                buttonBlue.isEnabled = false
                buttonGreen.isEnabled = false
                buttonCheck.isEnabled = false
            } else {
                buttonRed.isEnabled = true
                buttonYellow.isEnabled = true
                buttonBlue.isEnabled = true
                buttonGreen.isEnabled = true
                buttonCheck.isEnabled = true
            }
        })

        gameModel.gameSec.observe(this, Observer {
            gameModel.showSec(btnColor)
        })

        buttonStart.setOnClickListener {
            toast1.show()
            gameModel.init_game()
        }

        buttonCheck.setOnClickListener {
            if (!gameModel.checkSec()) {
                toast0.show()
            }
        }


        buttonRed.setOnClickListener {
            gameModel.addUserSec(1)
        }
        buttonGreen.setOnClickListener {
            gameModel.addUserSec(2)
        }
        buttonYellow.setOnClickListener {
            gameModel.addUserSec(3)
        }
        buttonBlue.setOnClickListener {
            gameModel.addUserSec(4)
        }
    }
}