package com.example.tasktimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val taskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chronometer = findViewById<Chronometer>(R.id.timer)
        val start = findViewById<Button>(R.id.button)
        val stop = findViewById<Button>(R.id.button2)
        val showNumber = findViewById<TextView>(R.id.textView)
        var running = false
        var pauseOffset: Long = 0

        chronometer.format = "Time: %s"
        chronometer.base = SystemClock.elapsedRealtime()

//        chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
//            if ((SystemClock.elapsedRealtime() - chronometer.base) >= 10000) {
//                chronometer.base = SystemClock.elapsedRealtime()
//                Toast.makeText(this@MainActivity, "Bing!", Toast.LENGTH_SHORT).show()
//            }
//        }

        start.setOnClickListener {
            if (!running) {
                Log.d("MyTime","before ${chronometer.base} ${SystemClock.elapsedRealtime()} $pauseOffset")
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset*10
                Log.d("MyTime","after ${chronometer.base} ${SystemClock.elapsedRealtime()}")
                chronometer.start()
                running = true
            }
        }

        stop.setOnClickListener {
            if (running) {
                chronometer.stop()
                Log.d("MyTime","stop ${chronometer.base} ${SystemClock.elapsedRealtime()}")
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base

                //This Code Will Show Description About The Time EX(9 Seconds)
                showNumber.text = chronometer.contentDescription

                //This Code Will Show The Time Passed in Seconds
                //(pauseOffset/1000).toString()

                running = false
            }
        }
        //to reset the timer
//        chronometer.base = SystemClock.elapsedRealtime()
//        pauseOffset = 0
    }
}