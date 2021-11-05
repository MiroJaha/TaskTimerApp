package com.example.tasktimerapp.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.databinding.ViewRvBinding
import com.example.tasktimerapp.room.Data

class RVMainAdapter(private val tasks: ArrayList<Data>) :
    RecyclerView.Adapter<RVMainAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ViewRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ViewRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val taskName = tasks[position].taskName
        val taskTime = tasks[position].taskTime

        holder.binding.apply {
            title.text = taskName
        }
    }

    override fun getItemCount() = tasks.size
}