package com.example.tasktimerapp

import android.os.Bundle
import android.view.View
import android.widget.*
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
    private val priorityList = arrayListOf("High", "Medium", "Low")

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

        onSpinnerSelected()
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
                        StyleableToast.makeText(this, "Updated Successfully", R.style.updateToast)
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
                        StyleableToast.makeText(this, "Updated Successfully", R.style.updateToast)
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
                        StyleableToast.makeText(this, "Updated Successfully", R.style.updateToast)
                    }
                    else -> {
                        StyleableToast.makeText(this, "Please Choose Priority", R.style.failToast)
                    }
                }
            } else {
                StyleableToast.makeText(this, "Please Enter Valid Values", R.style.failToast)
            }
            setHint()
        }
    }

    private fun checkEntry(): Boolean {
        var check=false
        if (taskNameEntry.text.isNotBlank()) {
            taskName = taskNameEntry.text.toString()
            check=true
        }
        if (taskDescriptionEntry.text.isNotBlank()){
            taskDescription= taskDescriptionEntry.text.toString()
            check=true
        }
        if (prioritySpinner.selectedItem.toString() != priority){
            check=true
        }
        return check
    }

    private fun setHint() {
        taskNameEntry.hint= "Task Name: $taskName"
        taskNameEntry.text.clear()
        taskNameEntry.clearFocus()
        taskDescriptionEntry.hint= "Task Description:\n$taskDescription"
        taskDescriptionEntry.text.clear()
        taskDescriptionEntry.clearFocus()
        for (i in 0 until priorityList.size){
            if (priority==priorityList[i]){
                prioritySpinner.setSelection(i)
            }
        }
    }

    private fun onSpinnerSelected() {
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

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}