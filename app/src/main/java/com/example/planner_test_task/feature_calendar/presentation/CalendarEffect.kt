package com.example.planner_test_task.feature_calendar.presentation

import com.example.planner_test_task.core_base.UiEffect

sealed class CalendarEffect : UiEffect {
    object NavigateToCreateTask : CalendarEffect()
    data class NavigateToTaskDetail(val taskId: Long) : CalendarEffect()
    data class ShowErrorToast(val message: String) : CalendarEffect()
}
