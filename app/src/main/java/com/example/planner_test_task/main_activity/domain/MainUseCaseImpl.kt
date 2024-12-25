package com.example.planner_test_task.main_activity.domain

import com.example.planner_test_task.main_activity.repository.MainRepository
import javax.inject.Inject


class MainUseCaseImpl @Inject constructor(
    private val repository: MainRepository
) : MainUseCase {
    override suspend fun initializeTasks() {
        val tasks = repository.loadTasks()
        repository.insertTasks(tasks)
    }
}
