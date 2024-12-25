package com.example.planner_test_task.core_navigation

import androidx.navigation.NavHostController

class DiaryNavigation(private val navController: NavHostController) {

    fun navigateToTaskDetail(taskId: Long) {
        navController.navigate(Screen.TaskDetail.createRoute(taskId))
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateToCreateTask() {
        navController.navigate(Screen.CreateTask.route)
    }

    fun getTaskIdFromCurrentRoute(): Long? {
        return navController.currentBackStackEntry?.arguments?.getString("taskId")?.toLongOrNull()
    }
}
