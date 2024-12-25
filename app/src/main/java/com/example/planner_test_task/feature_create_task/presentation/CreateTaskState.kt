package com.example.planner_test_task.feature_create_task.presentation

import com.example.planner_test_task.core_base.UiState

data class CreateTaskState(
    val name: String = "",
    val description: String = "",
    val selectedDate: Long = System.currentTimeMillis(),
    val startTime: String = "00:00",
    val endTime: String = "01:00",
    val eventMessage: String? = null
) : UiState
{
    val isFormValid: Boolean
        get() = name.length in 3..22 &&
                description.length in 5..80 &&
                selectedDate != 0L &&
                startTime.isNotEmpty() &&
                endTime.isNotEmpty()
}