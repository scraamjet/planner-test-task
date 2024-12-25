package com.example.planner_test_task.feature_calendar.repository

import com.example.planner_test_task.core_model.TaskEntity

interface CalendarRepository {
    suspend fun getTasksForDate(startDate: Long, endDate: Long): List<TaskEntity>
}