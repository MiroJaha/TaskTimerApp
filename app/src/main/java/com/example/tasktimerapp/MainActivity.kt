package com.example.tasktimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer= findViewById<Chronometer>(R.id.timer)
        val button= findViewById<Button>(R.id.button)
        val stop= findViewById<Button>(R.id.button2)

        button.setOnClickListener{
            timer.start()
        }

        stop.setOnClickListener{
            timer.stop()

        }

    }
}