package com.example.roomdatabaseexample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebenezer.gana.todoapp.PriorityLevel
import com.ebenezer.gana.todoapp.R
import com.ebenezer.gana.todoapp.Task
import com.ebenezer.gana.todoapp.databinding.ListItemBinding


class TaskAdapter(private val context: Context, private val listener: (Long) -> Unit) :
    ListAdapter<Task, TaskAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskAdapter.ViewHolder {

        return ViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition).id)
            }
        }

        fun bind(task: Task) {
            when (task.priority) {
                PriorityLevel.High.ordinal -> {
                    binding.taskPriority.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.colorPriorityHigh)
                    )
                }
                PriorityLevel.Medium.ordinal -> {
                    binding.taskPriority.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorPriorityMedium
                        )
                    )
                }
                else -> binding.taskPriority.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.colorPriorityLow)
                )
            }


            binding.taskTitle.text = task.title
            binding.taskDetail.text = task.details
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}