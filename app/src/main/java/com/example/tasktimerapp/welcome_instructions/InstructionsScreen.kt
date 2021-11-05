package com.example.tasktimerapp.welcome_instructions

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktimerapp.R

class InstructionsScreen : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.instructions_screen)

        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)






        with(sharedPreferences.edit()) {
            putInt("CheckWelcomeStatus",0)
            apply()
        }
    }
}