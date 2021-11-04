package com.example.tasktimerapp.rv

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.databinding.TasksRvBinding
import com.example.tasktimerapp.room.Data

class RVAdapter (private val tasks: ArrayList<Data>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: TasksRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(TasksRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val title = tasks[position].taskName
        val time = tasks[position].taskTime

        holder.binding.apply {
            taskName.text = "Task Name: $title"
            timer.base= SystemClock.elapsedRealtime() - time*1000
            totalTime.text = "Total Time: ${timer.contentDescription}"
        }
    }

    override fun getItemCount() = tasks.size
}