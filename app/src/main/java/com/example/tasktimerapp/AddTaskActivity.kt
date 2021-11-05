package com.example.tasktimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider

class AddTaskActivity : AppCompatActivity() {

    private val taskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }

    private lateinit var taskNameEntry: EditText
    private lateinit var taskDescriptionEntry: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var priority: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        taskNameEntry= findViewById(R.id.eTask)
        taskDescriptionEntry= findViewById(R.id.eDescription)
        prioritySpinner= findViewById(R.id.spinner)
        saveButton= findViewById(R.id.save_button)

        val priorityList= arrayListOf("High","Medium","Low")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, priorityList
        )
        prioritySpinner.adapter = adapter
        prioritySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                priority= priorityList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                priority= ""
            }
        }

    }
}