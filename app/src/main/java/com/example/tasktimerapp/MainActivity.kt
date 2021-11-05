package com.example.tasktimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.graphics.alpha
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.room.Data
import com.example.tasktimerapp.rv.RVAdapter
import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime
import kotlin.time.nanoseconds

class MainActivity : AppCompatActivity() {

    private val taskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tasksList = arrayListOf<Data>()

        val chronometer = findViewById<Chronometer>(R.id.timer)
        val start = findViewById<Button>(R.id.button)
        val stop = findViewById<Button>(R.id.button2)
        val rvMain = findViewById<RecyclerView>(R.id.mainRV)
        val showNumber = findViewById<TextView>(R.id.textView)
        var running = false
        var pauseOffset: Long = 0

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        chronometer.format = "Time: %s"
        chronometer.base = SystemClock.elapsedRealtime()

        val adapter = RVAdapter(tasksList)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)

        taskViewModel.getAllTasks().observe(this) {
            tasksList.clear()
            tasksList.addAll(it)
            adapter.notifyDataSetChanged()
        }

        //This Data Will Be Updated Each time The Timer Stop To Test The Data
        taskViewModel.updateTask(Data(1, "999", "NoTime", 0))

        //This Codes Will Listen to the timer everytime is ticking
//        chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
//            if ((SystemClock.elapsedRealtime() - chronometer.base) >= 10000) {
//                chronometer.base = SystemClock.elapsedRealtime()
//                Toast.makeText(this@MainActivity, "Bing!", Toast.LENGTH_SHORT).show()
//            }
//        }

        start.setOnClickListener {
            if (!running) {
                progressBar.isIndeterminate = true
                Log.d(
                    "MyTime",
                    "before ${chronometer.base} ${SystemClock.elapsedRealtime()} $pauseOffset"
                )
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset * 10
                Log.d("MyTime", "after ${chronometer.base} ${SystemClock.elapsedRealtime()}")
                chronometer.start()
                running = true
            }
        }

        stop.setOnClickListener {
            if (running) {
                progressBar.isIndeterminate = false
                progressBar.progress = 50
                Log.d("MyTime", progressBar.progress.toString())
                chronometer.stop()
                Log.d("MyTime", "stop ${chronometer.base} ${SystemClock.elapsedRealtime()}")
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base

                //This Code Will Show Description About The Time EX(9 Seconds)
                showNumber.text = chronometer.contentDescription

                //This Code Will Show The Time Passed in Seconds
                //pauseOffset/1000

                taskViewModel.updateTask(
                    Data(
                        1,
                        "999",
                        "NoTime",
                        tasksList[0].taskTime + pauseOffset / 1000
                    )
                )
                taskViewModel.addNewTask(
                    Data(
                        0,
                        "$pauseOffset",
                        showNumber.text.toString(),
                        pauseOffset / 1000
                    )
                )

                running = false
            }
        }
        //to reset the timer
//        chronometer.base = SystemClock.elapsedRealtime()
//        pauseOffset = 0
    }
}