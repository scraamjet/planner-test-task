package com.example.planner_test_task.feature_create_task.presentation

import androidx.lifecycle.viewModelScope
import com.example.planner_test_task.core_base.BaseViewModel
import com.example.planner_test_task.core_model.TaskEntity
import com.example.planner_test_task.feature_create_task.domain.CreateTaskUseCase
import com.example.planner_test_task.utils.TaskResult
import com.example.planner_test_task.utils.Utils.withTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase
) : BaseViewModel<CreateTaskEvent, CreateTaskState, CreateTaskEffect>() {

    override fun createInitialState(): CreateTaskState = CreateTaskState()

    override fun handleEvent(event: CreateTaskEvent) {
        when (event) {
            is CreateTaskEvent.NameChanged -> setState { copy(name = event.name) }
            is CreateTaskEvent.DescriptionChanged -> setState { copy(description = event.description) }
            is CreateTaskEvent.DateChanged -> setState { copy(selectedDate = event.date) }
            is CreateTaskEvent.StartTimeChanged -> setState { copy(startTime = event.time) }
            is CreateTaskEvent.EndTimeChanged -> setState { copy(endTime = event.time) }
            is CreateTaskEvent.CreateTaskClicked -> handleCreateTask()
        }
    }

    private fun handleCreateTask() {
        val state = currentState
            val startDateTime = state.selectedDate.withTime(state.startTime).roundToHourStart()
            val endDateTime = state.selectedDate.withTime(state.endTime).roundToHourStart()

            val newTask = TaskEntity(
                name = state.name,
                description = state.description,
                dateStart = startDateTime,
                dateFinish = endDateTime
            )

            viewModelScope.launch {
                val isTimeAvailable = createTaskUseCase.isTaskTimeAvailable(newTask)
                if (isTimeAvailable) {
                    createTaskUseCase.createTask(newTask).collect { result ->
                        when (result) {
                            is TaskResult.Success -> {
                                setEffect { CreateTaskEffect.TaskCreated }
                            }
                            is TaskResult.Error -> setEffect { CreateTaskEffect.ShowErrorToast(result.message) }
                        }
                    }
                } else {
                    setEffect { CreateTaskEffect.ShowErrorToast("Task already exists at this time") }
                }
            }
        }
    private fun Long.roundToHourStart(): Long {
        val instant = Instant.ofEpochMilli(this)
        return instant.truncatedTo(ChronoUnit.HOURS).toEpochMilli()
    }

}