package com.example.tasktimerapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrInterface
import com.r0adkll.slidr.model.SlidrPosition

class TaskDetailsActivity : AppCompatActivity() {

    private val taskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }

    private lateinit var slidr: SlidrInterface
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        slidrBuilding()
    }

    private fun slidrBuilding() {
        val config = SlidrConfig.Builder()
            .primaryColor(resources.getColor(R.color.design_default_color_primary))
            .secondaryColor(resources.getColor(R.color.design_default_color_secondary))
            .position(SlidrPosition.LEFT) //LEFT|RIGHT|TOP|BOTTOM|VERTICAL|HORIZONTAL
            .sensitivity(1f)
            .scrimColor(Color.BLACK)
            .scrimStartAlpha(0.8f)
            .scrimEndAlpha(0f)
            .velocityThreshold(2400f)
            .distanceThreshold(0.25f)
            .edge(false) //true|false
            .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
            .build() //You can add .listener(new SlidrListener(){...}) before build

        slidr= Slidr.attach(this,config)
    }
}