package com.example.planner_test_task.main_activity.repository

import com.example.planner_test_task.core_data.json.JsonDataSource
import com.example.planner_test_task.core_data.room.TaskDao
import com.example.planner_test_task.core_model.TaskEntity
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor(
    private val jsonDataSource: JsonDataSource,
    private val taskDao: TaskDao
) : MainRepository {
    override suspend fun loadTasks(): List<TaskEntity> {
        return jsonDataSource.loadTasks()
    }

    override suspend fun insertTasks(tasks: List<TaskEntity>) {
        taskDao.insertTasks(tasks)
    }
}
