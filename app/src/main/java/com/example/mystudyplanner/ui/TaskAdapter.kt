package com.example.mystudyplanner.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mystudyplanner.R
import com.example.mystudyplanner.data.Task
import com.example.mystudyplanner.databinding.ItemTaskBinding

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onCheckClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                tvTaskTitle.text = task.title
                tvSubject.text = "📚 ${task.subject}"
                tvDeadline.text = "📅 ${task.deadline}"
                cbCompleted.isChecked = task.isCompleted

                // Strikethrough if completed
                if (task.isCompleted) {
                    tvTaskTitle.paintFlags = tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvTaskTitle.alpha = 0.5f
                    tvSubject.alpha = 0.5f
                    tvDeadline.alpha = 0.5f
                } else {
                    tvTaskTitle.paintFlags = tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    tvTaskTitle.alpha = 1.0f
                    tvSubject.alpha = 1.0f
                    tvDeadline.alpha = 1.0f
                }

                // Priority badge color
                val (priorityText, bgColor) = when (task.priority) {
                    "High"   -> Pair("🔴 High",   R.color.priority_high)
                    "Medium" -> Pair("🟡 Medium", R.color.priority_medium)
                    else     -> Pair("🟢 Low",    R.color.priority_low)
                }
                tvPriority.text = priorityText
                tvPriority.backgroundTintList =
                    ContextCompat.getColorStateList(root.context, bgColor)

                // Card background alternates by priority
                val cardColor = when (task.priority) {
                    "High"   -> R.color.card_pink
                    "Medium" -> R.color.card_lavender
                    else     -> R.color.card_mint
                }
                cardTask.setCardBackgroundColor(
                    ContextCompat.getColor(root.context, cardColor)
                )

                // Listeners
                cbCompleted.setOnClickListener { onCheckClick(task) }
                btnDelete.setOnClickListener { onDeleteClick(task) }
                root.setOnClickListener { onTaskClick(task) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}