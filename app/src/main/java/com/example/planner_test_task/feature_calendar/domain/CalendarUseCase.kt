package com.example.planner_test_task.feature_calendar.domain

import com.example.planner_test_task.core_model.Task
import com.example.planner_test_task.utils.TaskResult
import kotlinx.coroutines.flow.Flow

interface CalendarUseCase {
    suspend fun getTasksForDate(date: Long): Flow<TaskResult<List<Task>?>>
}