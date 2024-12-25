package com.example.planner_test_task.core_data.mapper

import com.example.planner_test_task.core_model.Task
import com.example.planner_test_task.core_model.TaskEntity


class TaskEntityToTaskMapper {
    fun map(taskEntity: TaskEntity): Task {
        return Task(
            id = taskEntity.id,
            name = taskEntity.name,
            dateStart = taskEntity.dateStart,
            dateFinish = taskEntity.dateFinish,
            description = taskEntity.description
        )
    }
    fun map(task: Task): TaskEntity {
        return TaskEntity(
            id = task.id,
            name = task.name,
            dateStart = task.dateStart,
            dateFinish = task.dateFinish,
            description = task.description
        )

    }
}
