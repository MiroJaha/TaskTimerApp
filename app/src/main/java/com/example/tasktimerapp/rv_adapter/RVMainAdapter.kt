package com.example.tasktimerapp.rv_adapter

import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.databinding.MainRvBinding
import com.example.tasktimerapp.room.Data
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection

class RVMainAdapter(private val context:Context,private val tasks: ArrayList<Data>) :
    RecyclerView.Adapter<RVMainAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: MainRvBinding) : RecyclerView.ViewHolder(binding.root)

    private var expansionsCollection: ExpansionLayoutCollection = ExpansionLayoutCollection()

    init {
        expansionsCollection.openOnlyOne(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            MainRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


        val task= tasks[position]

        holder.binding.apply {
            expansionsCollection.add(expansionLayout)

            timer.base= SystemClock.elapsedRealtime() - (task.taskTime*1000)

            title.text = tasks[position].taskName

            descriptionAndTime.text= "Description: ${tasks[position].taskDescription}\nTotal Time: ${timer.contentDescription}"

            when(task.priority){
                "High" -> {
                    mainLay.setBackgroundColor(ContextCompat.getColor(context, R.color.high))
                }
                "Medium" -> {
                    mainLay.setBackgroundColor(ContextCompat.getColor(context, R.color.medium))
                }
                "Low" -> {
                    mainLay.setBackgroundColor(ContextCompat.getColor(context, R.color.low))
                }
            }

            if (task.isRunning){
                running.text = "Running"
            }
            else
                running.text = "Stop"
        }
    }

    override fun getItemCount() = tasks.size

    fun updateRVMain(){
        notifyDataSetChanged()
    }
}