package dev.moisesborges.expandablelistdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import dev.moisesborges.expandablelistdemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TasksAdapter

    private var tasks: List<Task> =
        listOf(
            Task.MainTask(uuid(), "First Task"),
            Task.MainTask(uuid(), "Second Task"),
            Task.MainTask(uuid(),"Third Task")
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TasksAdapter()
        adapter.tasks = tasks
        adapter.mainTaskClickEvent = ::handleTaskClick
        binding.tasksRecyclerView.adapter = adapter
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun handleTaskClick(index: Int) {
        val subTasks = listOf<Task>(
            Task.SubTask(uuid(), "Subtask 1"),
            Task.SubTask(uuid(), "Subtask 2"),
            Task.SubTask(uuid(), "Subtask 3"),
            Task.SubTask(uuid(), "Subtask 4"),
        )
        val selectedTask = tasks[index]
        if (selectedTask !is Task.MainTask) {
            Log.e("MainActivity", "Only MainTask selection is supported")
            return
        }
        val newTasks = tasks.toMutableList()
        if (selectedTask.expanded) {
            repeat(subTasks.size) {
                newTasks.removeAt(index + 1)
            }
        } else {
            newTasks.addAll(index + 1, subTasks)
        }
        newTasks[index] = selectedTask.copy(expanded = !selectedTask.expanded)
        tasks = newTasks
        adapter.tasks = tasks
    }
}

private fun uuid() =
    UUID.randomUUID().toString()

