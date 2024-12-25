package com.example.planner_test_task.feature_task_detail.repository

import com.example.planner_test_task.core_model.TaskEntity

interface TaskDetailRepository {
    suspend fun getTaskById(taskId: Long): TaskEntity
}