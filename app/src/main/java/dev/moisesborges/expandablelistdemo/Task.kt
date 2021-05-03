package dev.moisesborges.expandablelistdemo

sealed class Task {

    data class MainTask(
        var id: String,
        var name: String,
        var expanded: Boolean = false,
        var subTasks : List<SubTask>
    ) : Task()

    data class SubTask(val id: String, val name: String) : Task()
}
