package com.example.tasktimerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimerapp.room.Data
import io.github.muddz.styleabletoast.StyleableToast

class EditTasks : AppCompatActivity() {

    private val taskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }

    private lateinit var taskNameEntry: EditText
    private lateinit var taskDescriptionEntry: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var taskName: String
    private lateinit var taskDescription: String
    private lateinit var priority : String
    private var pk= 0
    private lateinit var data : Data
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_task)

        taskNameEntry = findViewById(R.id.eTask)
        taskDescriptionEntry = findViewById(R.id.eDescription)
        prioritySpinner = findViewById(R.id.spinner)
        saveButton = findViewById(R.id.save_button)

        bundle= intent.extras!!
        pk= bundle.getInt("pk")
        data= taskViewModel.getTask(pk)
        taskName= data.taskName
        taskDescription= data.taskDescription
        priority= data.priority

        setHint()
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
                                "High"
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
                                "Medium"
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
                                "Low"
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
        return false
    }

    private fun setHint() {
        taskNameEntry.hint= "Task Name: $taskName"
    }
}