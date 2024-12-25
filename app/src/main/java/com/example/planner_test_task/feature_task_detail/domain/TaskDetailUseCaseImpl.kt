package com.example.planner_test_task.feature_task_detail.domain

import com.example.planner_test_task.core_data.mapper.TaskEntityToTaskMapper
import com.example.planner_test_task.core_model.Task
import com.example.planner_test_task.feature_task_detail.repository.TaskDetailRepository
import com.example.planner_test_task.utils.TaskResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class TaskDetailUseCaseImpl @Inject constructor(
    private val repository: TaskDetailRepository,
    private val mapper: TaskEntityToTaskMapper
) : TaskDetailUseCase {
    override suspend fun getTaskById(taskId: Long): Flow<TaskResult<Task?>> = flow {
        try {
            val taskEntity = repository.getTaskById(taskId)
            val task = mapper.map(taskEntity)
            emit(TaskResult.Success(task))
        } catch (e: Exception) {
            emit(TaskResult.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}
