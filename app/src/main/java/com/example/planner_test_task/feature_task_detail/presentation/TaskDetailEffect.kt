package com.example.planner_test_task.feature_task_detail.presentation

import com.example.planner_test_task.core_base.UiEffect

sealed class TaskDetailEffect: UiEffect {
    data class ShowError(val message: String) : TaskDetailEffect()
    object NavigateBack : TaskDetailEffect()
}