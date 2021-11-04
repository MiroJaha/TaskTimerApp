package com.example.tasktimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chronometer= findViewById<Chronometer>(R.id.timer)
        val start= findViewById<Button>(R.id.button)
        val stop= findViewById<Button>(R.id.button2)
        var running= false
        var pauseOffset: Long= 0

        chronometer.format = "Time: %s";
        chronometer.base = SystemClock.elapsedRealtime();

        chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
            if ((SystemClock.elapsedRealtime() - chronometer.base) >= 10000) {
                chronometer.base = SystemClock.elapsedRealtime();
                Toast.makeText(this@MainActivity, "Bing!", Toast.LENGTH_SHORT).show();
            }
        }

        start.setOnClickListener{
            if (!running) {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset;
                chronometer.start();
                running = true;
            }
        }

        stop.setOnClickListener{
            if (running) {
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base;
                running = false;
            }
        }
        //to reset the timer
//        chronometer.base = SystemClock.elapsedRealtime();
//        pauseOffset = 0;
    }
}