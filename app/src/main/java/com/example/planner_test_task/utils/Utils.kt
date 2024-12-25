package com.example.planner_test_task.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Utils {

    fun Long.withTime(time: String): Long {
        val dateTime = Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .withHour(time.substringBefore(":").toInt())
            .withMinute(time.substringAfter(":").toInt())
            .toInstant()
        return dateTime.toEpochMilli()
    }

    fun Long.formatDateForTaskDetail(): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm")
            .withZone(ZoneId.systemDefault())
        return formatter.format(Instant.ofEpochMilli(this))
    }

    fun Long.formatDateTaskItem(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).format(formatter)
    }

}
