package com.example.planner_test_task.feature_calendar.presentation

import com.example.planner_test_task.core_base.UiState
import com.example.planner_test_task.core_model.Task

data class CalendarState(
    val selectedDate: Long = System.currentTimeMillis(),
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState


