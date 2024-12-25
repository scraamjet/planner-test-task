package com.example.planner_test_task.feature_task_detail.presentation

import com.example.planner_test_task.core_base.UiState
import com.example.planner_test_task.core_model.Task

data class TaskDetailState(
    val task: Task? = null,
    val isLoading: Boolean = false,
) : UiState
