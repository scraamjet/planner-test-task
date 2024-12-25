package com.example.planner_test_task.utils

sealed interface TaskResult<T> {

        data class Error<T>(val message: String?): TaskResult<T?>

        data class Success<T>(val data: T?): TaskResult<T?>
}