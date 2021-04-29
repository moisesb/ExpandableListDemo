package dev.moisesborges.expandablelistdemo

import androidx.recyclerview.widget.DiffUtil

class TaskDiffUtilCallback(
    private val oldTasks: List<Task>,
    private val newTasks: List<Task>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTasks.size

    override fun getNewListSize(): Int = newTasks.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTask = oldTasks[oldItemPosition]
        val newTask = newTasks[newItemPosition]
        return if (oldTask is Task.MainTask && newTask is Task.MainTask) {
            oldTask.id == newTask.id
        } else if (oldTask is Task.SubTask && newTask is Task.SubTask) {
            oldTask.id == newTask.id
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldTasks[oldItemPosition] == newTasks[newItemPosition]
}