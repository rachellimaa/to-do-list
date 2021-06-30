package com.rachellima.todolist.datasource

import com.rachellima.todolist.model.Task

object TaskDataSource {
    private val list = mutableListOf<Task>()

    fun getList() = list

    fun insertTask(task: Task) {
        list.add(task.copy(id = list.size + 1))
    }
}