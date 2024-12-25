package com.example.planner_test_task.core_model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String
)