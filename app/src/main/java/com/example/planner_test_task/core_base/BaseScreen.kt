package com.example.planner_test_task.core_base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.planner_test_task.core_navigation.DiaryNavigation

abstract class BaseScreen<VM : BaseViewModel<Event, State, Effect>, Event : UiEvent, State : UiState, Effect : UiEffect> {

    lateinit var viewModel: VM
    lateinit var navigation: DiaryNavigation

    @Composable
    abstract fun Screen(state: State)

    @Composable
    abstract fun OnEvent()

    @Composable
    fun Create(viewModel: VM, navController: NavHostController) {
        this@BaseScreen.viewModel = viewModel
        this@BaseScreen.navigation = DiaryNavigation(navController)

        val state by viewModel.uiState.collectAsState()

        Screen(state)

        OnEvent()
    }
}
