package com.example.tasktimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimerapp.room.Data
import io.github.muddz.styleabletoast.StyleableToast

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

        taskNameEntry = findViewById(R.id.eTask)
        taskDescriptionEntry = findViewById(R.id.eDescription)
        prioritySpinner = findViewById(R.id.spinner)
        saveButton = findViewById(R.id.save_button)

        onSpinnerSelected()

        onSaveButtonClicked()
    }

    private fun onSaveButtonClicked() {
        saveButton.setOnClickListener {
            if (checkEntry()) {
                when (priority) {
                    "High" -> {
                        taskViewModel.addNewTask(
                            Data(
                                0,
                                taskNameEntry.text.toString(),
                                taskDescriptionEntry.text.toString(),
                                0,
                                ContextCompat.getColor(this, R.color.high)
                            )
                        )
                        StyleableToast.makeText(this, "Add Successfully", R.style.addToast)
                    }
                    "Medium" -> {
                        taskViewModel.addNewTask(
                            Data(
                                0,
                                taskNameEntry.text.toString(),
                                taskDescriptionEntry.text.toString(),
                                0,
                                ContextCompat.getColor(this, R.color.medium)
                            )
                        )
                        StyleableToast.makeText(this, "Add Successfully", R.style.addToast)
                    }
                    "Low" -> {
                        taskViewModel.addNewTask(
                            Data(
                                0,
                                taskNameEntry.text.toString(),
                                taskDescriptionEntry.text.toString(),
                                0,
                                ContextCompat.getColor(this, R.color.low)
                            )
                        )
                        StyleableToast.makeText(this, "Add Successfully", R.style.addToast)
                    }
                    else -> {
                        StyleableToast.makeText(this, "Please Choose Priority", R.style.failToast)
                    }
                }
            } else {
                StyleableToast.makeText(this, "Please Enter Valid Values", R.style.failToast)
            }
        }
    }

    private fun checkEntry(): Boolean {
        if (taskNameEntry.text.isBlank())
            return false
        if (taskDescriptionEntry.text.isBlank())
            return false
        return true
    }

    private fun onSpinnerSelected() {
        val priorityList = arrayListOf("High", "Medium", "Low")
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
                priority = priorityList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                priority = ""
            }
        }
    }
}