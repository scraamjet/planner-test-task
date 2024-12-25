package com.example.planner_test_task.feature_calendar.presentation

import androidx.lifecycle.viewModelScope
import com.example.planner_test_task.core_base.BaseViewModel
import com.example.planner_test_task.feature_calendar.domain.CalendarUseCase
import com.example.planner_test_task.utils.TaskResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val calendarUseCase: CalendarUseCase) : BaseViewModel<CalendarEvent, CalendarState, CalendarEffect>() {

    override fun createInitialState(): CalendarState {
        return CalendarState()
    }

    override fun handleEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.SelectDate -> {
                setState { copy(selectedDate = event.date, isLoading = true, error = null) }
                loadTasksForSelectedDate()
            }
            is CalendarEvent.CreateTaskClick -> {
                setEffect { CalendarEffect.NavigateToCreateTask }
            }
            is CalendarEvent.TaskClick -> {
                setEffect { CalendarEffect.NavigateToTaskDetail(event.taskId) }
            }
        }
    }

    private fun loadTasksForSelectedDate() {
        viewModelScope.launch {
            calendarUseCase.getTasksForDate(currentState.selectedDate).collect { result ->
                when (result) {
                    is TaskResult.Success -> {
                        setState { copy(tasks = result.data ?: emptyList(), isLoading = false) }
                    }
                    is TaskResult.Error -> {
                        setState { copy(isLoading = false) }
                        setEffect { CalendarEffect.ShowErrorToast("Unknown error") }
                    }
                }
            }
        }
    }
}