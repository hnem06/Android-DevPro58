package com.devpro.android58_day10.example.ex2

class Task(val id:String,  title:String) {
    var title:String = title
        private set

    private var isCompleted: Boolean = false

    fun markDone(){
        isCompleted = true
    }

    fun isComplete(): Boolean = isCompleted

    override fun toString(): String {
        return "Task(id='$id', title='$title', isCompleted=$isCompleted)"
    }
}