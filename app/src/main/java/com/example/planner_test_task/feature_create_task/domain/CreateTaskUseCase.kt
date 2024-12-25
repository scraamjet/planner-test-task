package com.example.planner_test_task.feature_create_task.domain

import com.example.planner_test_task.core_model.TaskEntity
import com.example.planner_test_task.utils.TaskResult
import kotlinx.coroutines.flow.Flow

interface CreateTaskUseCase {
    suspend fun createTask(task: TaskEntity): Flow<TaskResult<Unit?>>
    suspend fun isTaskTimeAvailable(task: TaskEntity): Boolean
}
