package com.example.tasktimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.room.Data
import com.example.tasktimerapp.rv_adapter.RVMainAdapter
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {

    private val taskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }
    private lateinit var adapter: RVMainAdapter

    private lateinit var rvItem: RecyclerView
    private lateinit var tasksList: ArrayList<Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItem= findViewById(R.id.rvItems)
        tasksList= arrayListOf()

        adapter= RVMainAdapter(tasksList)
        rvItem.adapter= adapter
        rvItem.layoutManager= LinearLayoutManager(this)

        taskViewModel.getAllTasks().observe(this){
            tasksList.clear()
            tasksList.addAll(it)
            adapter.updateRVMain()
        }


    }
}