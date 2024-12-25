package com.example.planner_test_task.feature_task_detail.presentation

import androidx.lifecycle.viewModelScope
import com.example.planner_test_task.core_base.BaseViewModel
import com.example.planner_test_task.feature_task_detail.domain.TaskDetailUseCase
import com.example.planner_test_task.utils.TaskResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskDetailUseCase: TaskDetailUseCase
) : BaseViewModel<TaskDetailEvent, TaskDetailState, TaskDetailEffect>() {

    override fun createInitialState(): TaskDetailState = TaskDetailState()

    override fun handleEvent(event: TaskDetailEvent) {
        when (event) {
            is TaskDetailEvent.LoadTask -> loadTask(event.taskId)
        }
    }

    private fun loadTask(taskId: Long) {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            taskDetailUseCase.getTaskById(taskId).collect { result ->
                when (result) {
                    is TaskResult.Success -> {
                        setState {
                            copy(
                                task = result.data,
                                isLoading = false,
                            )
                        }
                    }
                    is TaskResult.Error -> {
                        setState {
                            copy(isLoading = false)
                        }
                        setEffect { TaskDetailEffect.ShowError("Unknown error") }
                    }
                }
            }
        }
    }
}


