package com.rachellima.todolist.model

data class Task(
    val title: String,
    val hour: String,
    val description: String,
    val date: String,
    val id: Int = 0
)