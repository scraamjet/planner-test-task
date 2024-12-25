package com.example.planner_test_task.main_activity.repository

import com.example.planner_test_task.core_model.TaskEntity

interface MainRepository {
    suspend fun loadTasks(): List<TaskEntity>
    suspend fun insertTasks(tasks: List<TaskEntity>)
}