package com.example.planner_test_task.core_model

data class Task(
    val id: Long,
    val name: String,
    val dateStart: Long,
    val dateFinish: Long,
    val description: String
)
