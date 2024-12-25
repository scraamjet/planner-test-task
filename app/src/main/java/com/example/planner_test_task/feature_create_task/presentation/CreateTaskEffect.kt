package com.example.planner_test_task.feature_create_task.presentation

import com.example.planner_test_task.core_base.UiEffect

sealed class CreateTaskEffect : UiEffect {
    object TaskCreated : CreateTaskEffect()
    object NavigateBack : CreateTaskEffect()
    data class ShowErrorToast(val message: String?) : CreateTaskEffect()
}
