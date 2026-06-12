package com.devpro.android58_day10.example.ex2

class TaskServices(private val notificationSender: NotificationSender) {
    private val listTask = mutableListOf<Task>()
    private val taskUserMap = mutableMapOf<String, User>()

    fun createTask(user: User, id: String, title: String):Boolean {
        val task = Task(id, title)
        listTask.add(task)
        taskUserMap[id] = user
        println("Added task: $task")
        notificationSender.sendNotification(user, "Task '$title' has been created.")
        return true
    }

    fun markDone(id:String): Boolean{
        listTask.find { it.id == id }?.let {
            it.markDone()
            println("Marked done: idTask $id")
            val user = taskUserMap[id]
            if (user != null) {
                notificationSender.sendNotification(user, "Task '${it.title}' has been completed.")
            }
            return true
        }
        return false
    }

    fun listOpenTask(): List<Task>{
        return listTask.filter { !it.isComplete() }
    }
}