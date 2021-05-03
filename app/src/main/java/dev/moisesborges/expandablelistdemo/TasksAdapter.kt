package dev.moisesborges.expandablelistdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.moisesborges.expandablelistdemo.databinding.ViewMainTaskBinding
import dev.moisesborges.expandablelistdemo.databinding.ViewSubTaskBinding

private const val MAIN_TASK_VIEW_TYPE = 1
private const val SUB_TASK_VIEW_TYPE = 2

typealias ClickEvent = (String) -> Unit

class TasksAdapter : RecyclerView.Adapter<TaskViewHolder>() {

    var tasks: List<Task> = emptyList()
        set(value) {
            val oldTasks = field
            field = value
            DiffUtil.calculateDiff(TaskDiffUtilCallback(oldTasks = oldTasks, newTasks = value))
                .dispatchUpdatesTo(this)
        }

    var mainTaskClickEvent: ClickEvent? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MAIN_TASK_VIEW_TYPE -> TaskViewHolder.MainTaskViewHolder(
                ViewMainTaskBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            SUB_TASK_VIEW_TYPE -> TaskViewHolder.SubTaskViewHolder(
                ViewSubTaskBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> throw IllegalStateException("Type $viewType not supported")
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        when (holder) {
            is TaskViewHolder.MainTaskViewHolder -> {
                holder.bind(task, mainTaskClickEvent)
            }

            is TaskViewHolder.SubTaskViewHolder -> {
                holder.bind(task)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (tasks[position]) {
            is Task.MainTask -> MAIN_TASK_VIEW_TYPE
            is Task.SubTask -> SUB_TASK_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int = tasks.size
}

sealed class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class MainTaskViewHolder(private val binding: ViewMainTaskBinding) :
        TaskViewHolder(binding.root) {

        fun bind(task: Task, clickEvent: ClickEvent?) {
            if (task !is Task.MainTask) {
                throw IllegalArgumentException("Type should be MainTask")
            }
            binding.taskName.text = task.name
            binding.root.setOnClickListener { clickEvent?.invoke(task.id) }

        }

    }

    class SubTaskViewHolder(private val binding: ViewSubTaskBinding) :
        TaskViewHolder(binding.root) {

        fun bind(task: Task) {
            if (task !is Task.SubTask) {
                throw IllegalArgumentException("Type should be SubTask")
            }
            binding.taskName.text = task.name
        }
    }
}