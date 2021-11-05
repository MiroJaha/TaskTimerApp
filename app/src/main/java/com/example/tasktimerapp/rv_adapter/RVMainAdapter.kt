package com.example.tasktimerapp.rv_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.databinding.MainRvBinding
import com.example.tasktimerapp.room.Data

class RVMainAdapter(private val context:Context,private val tasks: ArrayList<Data>) :
    RecyclerView.Adapter<RVMainAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: MainRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            MainRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val time= ""
        holder.binding.apply {
            title.text = tasks[position].taskName
            descriptionAndTime.text= "Description: ${tasks[position].taskDescription}\nTotal Time: ${tasks[position].taskName}"
            when(tasks[position].priority){
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
        }
    }

    override fun getItemCount() = tasks.size

    fun updateRVMain(){
        notifyDataSetChanged()
    }
}