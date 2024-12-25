package com.example.planner_test_task.feature_task_detail.repository

import com.example.planner_test_task.core_data.room.TaskDao
import com.example.planner_test_task.core_model.TaskEntity
import javax.inject.Inject


class TaskDetailRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskDetailRepository {
    override suspend fun getTaskById(taskId: Long): TaskEntity {
        return taskDao.getTaskById(taskId)
    }
}
