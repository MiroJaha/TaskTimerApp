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
    private var savedTime: Long=0
    private var previousPosition= -1

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

        tasksList= arrayListOf()
        adapter= RVMainAdapter(this,tasksList)
        rvItem.adapter= adapter
        rvItem.layoutManager= LinearLayoutManager(this)

        adapterListener()

        taskViewModel.getAllTasks().observe(this){
            tasksList.clear()
            tasksList.addAll(it)
            adapter.updateRVMain()
        }

        swipeTasksBuilder()

        addButtonsListeners()
    }

    private fun adapterListener() {
        adapter.setOnItemClickListener(object : RVMainAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                if (previousPosition == position && running){
                    timer.stop()
                    pauseOffset = SystemClock.elapsedRealtime() - timer.base
                    val second: Int= (pauseOffset / 1000).toInt()
                    progressBar.isIndeterminate = false
                    savedTime= pauseOffset - savedTime
                    progressBar.progress = (savedTime - (second*1000)).toInt()
                    running = false
                    taskViewModel.updateTaskTime(tasksList[position].taskTime + pauseOffset/1000 ,tasksList[position].pk)
                    taskViewModel.updateTaskStatus(false, tasksList[position].pk)
                }
                else if (previousPosition == position && !running){
                    progressBar.isIndeterminate = true
                    timer.base = SystemClock.elapsedRealtime() - pauseOffset
                    timer.start()
                    running = true
                    taskViewModel.updateTaskStatus(true, tasksList[position].pk)
                }
                else if (running){
                    reset.performClick()
                    taskViewModel.updateTaskStatus(false, tasksList[previousPosition].pk)
                    taskViewModel.updateTaskStatus(true, tasksList[position].pk)
                    savedTime =0
                    previousPosition=position
                }
                else{
                    progressBar.isIndeterminate = true
                    timer.start()
                    timer.base = SystemClock.elapsedRealtime()
                    pauseOffset = 0
                    running = true
                    taskViewModel.updateTaskStatus(true, tasksList[position].pk)
                    previousPosition = position
                }
            }
        })
    }

    private fun addButtonsListeners() {
        reset.setOnClickListener{
            if (running){
                progressBar.isIndeterminate = false
                progressBar.isIndeterminate = true
                val nowTime = (SystemClock.elapsedRealtime() - timer.base)/1000
                taskViewModel.updateTaskTime(tasksList[previousPosition].taskTime+nowTime,tasksList[previousPosition].pk)
                timer.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
            }
            else {
                progressBar.isIndeterminate = false
                timer.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
            }
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