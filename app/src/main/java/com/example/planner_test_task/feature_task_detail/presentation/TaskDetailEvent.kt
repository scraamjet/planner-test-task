package com.example.planner_test_task.feature_task_detail.presentation

import com.example.planner_test_task.core_base.UiEvent

sealed class TaskDetailEvent : UiEvent {
    data class LoadTask(val taskId: Long) : TaskDetailEvent()
}

