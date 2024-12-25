package com.example.planner_test_task.feature_create_task.presentation

import com.example.planner_test_task.core_base.UiEvent


sealed class CreateTaskEvent : UiEvent {
    data class NameChanged(val name: String) : CreateTaskEvent()
    data class DescriptionChanged(val description: String) : CreateTaskEvent()
    data class DateChanged(val date: Long) : CreateTaskEvent()
    data class StartTimeChanged(val time: String) : CreateTaskEvent()
    data class EndTimeChanged(val time: String) : CreateTaskEvent()
    object CreateTaskClicked : CreateTaskEvent()
}
