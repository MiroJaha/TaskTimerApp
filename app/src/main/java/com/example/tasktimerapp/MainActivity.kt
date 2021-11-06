package com.example.tasktimerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.room.Data
import com.example.tasktimerapp.rv_adapter.RVMainAdapter
import com.example.tasktimerapp.rv_adapter.SwipeTasks
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {

    private val taskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }
    private lateinit var adapter: RVMainAdapter

    private lateinit var rvItem: RecyclerView
    private lateinit var taskName: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var timer: Chronometer
    private lateinit var reset: TextView
    private lateinit var addButton: ImageView
    private lateinit var showAllButton: ImageView

    private lateinit var tasksList: ArrayList<Data>
    private var running = false
    private var pauseOffset: Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItem= findViewById(R.id.rvItems)
        taskName= findViewById(R.id.taskName)
        progressBar= findViewById(R.id.progressBar)
        timer= findViewById(R.id.timer)
        reset= findViewById(R.id.reset)
        addButton= findViewById(R.id.bAdd)
        showAllButton= findViewById(R.id.showAll)

        taskName.text = "No Task Started"
        timer.base = SystemClock.elapsedRealtime()

        adapter= RVMainAdapter(this,tasksList)
        rvItem.adapter= adapter
        rvItem.layoutManager= LinearLayoutManager(this)

        tasksList= arrayListOf()
        taskViewModel.getAllTasks().observe(this){
            tasksList.clear()
            tasksList.addAll(it)
            adapter.updateRVMain()
        }

        adapterListener()

        swipeTasksBuilder()

        addButtonsListeners()
    }

    private fun adapterListener() {

    }

    private fun startTimer(position: Int){
        if (!running) {
            progressBar.isIndeterminate = true
            timer.base = SystemClock.elapsedRealtime() - pauseOffset
            timer.start()
            running = true
        }
        if (running) {
            progressBar.isIndeterminate = false

            progressBar.progress = 50
            timer.stop()

            pauseOffset = SystemClock.elapsedRealtime() - timer.base


            //This Code Will Show The Time Passed in Seconds
            //pauseOffset/1000

            taskViewModel.updateTask(
                Data(
                    1,
                    "999",
                    "NoTime",
                    tasksList[position].taskTime + pauseOffset / 1000,
                    ""
                )
            )

            running = false
        }
    }

    private fun addButtonsListeners() {
        reset.setOnClickListener{
            timer.base = SystemClock.elapsedRealtime()
            pauseOffset = 0
        }
        addButton.setOnClickListener{
            startActivity(Intent(this,AddTaskActivity::class.java))
        }
        showAllButton.setOnClickListener{
            startActivity(Intent(this,TaskDetailsActivity::class.java))
        }
    }

    private fun swipeTasksBuilder() {
        val swipeTasks= object :SwipeTasks(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction){
                    ItemTouchHelper.RIGHT -> {
                        val intent= Intent(this@MainActivity,EditTasks::class.java)
                        intent.putExtra("pk",tasksList[viewHolder.adapterPosition].pk)
                        startActivity(intent)
                    }
                    ItemTouchHelper.LEFT -> {
                        taskViewModel.deleteTask(tasksList[viewHolder.adapterPosition])
                    }
                }
            }
        }
        val touchHelper= ItemTouchHelper (swipeTasks)
        touchHelper.attachToRecyclerView(rvItem)
    }
}