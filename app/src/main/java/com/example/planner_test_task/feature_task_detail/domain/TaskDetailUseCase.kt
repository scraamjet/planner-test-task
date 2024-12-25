package com.example.planner_test_task.feature_task_detail.domain

import com.example.planner_test_task.core_model.Task
import com.example.planner_test_task.utils.TaskResult
import kotlinx.coroutines.flow.Flow

interface TaskDetailUseCase {
    suspend fun getTaskById(taskId: Long): Flow<TaskResult<Task?>>
}