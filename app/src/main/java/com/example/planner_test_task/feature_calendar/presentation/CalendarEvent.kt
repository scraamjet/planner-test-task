package com.example.planner_test_task.feature_calendar.presentation

import com.example.planner_test_task.core_base.UiEvent

sealed class CalendarEvent : UiEvent {
    data class SelectDate(val date: Long) : CalendarEvent()
    data class TaskClick(val taskId: Long) : CalendarEvent()
    object CreateTaskClick : CalendarEvent()
}