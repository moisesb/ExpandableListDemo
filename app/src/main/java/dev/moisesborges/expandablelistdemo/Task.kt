package dev.moisesborges.expandablelistdemo

sealed class Task {

    data class MainTask(
        val id: String, 
        val name: String,
        val expanded: Boolean = false
    ) : Task()

    data class SubTask(val id: String, val name: String) : Task()
}
