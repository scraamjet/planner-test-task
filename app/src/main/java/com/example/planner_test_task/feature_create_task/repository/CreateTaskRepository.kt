package com.example.planner_test_task.feature_create_task.repository

import com.example.planner_test_task.core_model.TaskEntity


interface CreateTaskRepository {
    suspend fun saveTask(taskEntity: TaskEntity)
    suspend fun isTimeAvailable(task: TaskEntity): Boolean
}