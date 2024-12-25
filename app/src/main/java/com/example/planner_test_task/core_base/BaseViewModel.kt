package com.example.planner_test_task.core_base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface UiEvent
interface UiState
interface UiEffect

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State> get() = _uiState.asStateFlow()

    val currentState: State
        get() = _uiState.value

    fun setState(reduce: State.() -> State) {
        _uiState.value = _uiState.value.reduce()
    }

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> get() = _event.asSharedFlow()

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected abstract fun handleEvent(event: Event)

    private val _effect: Channel<Effect> = Channel(Channel.BUFFERED)
    val effect: Flow<Effect> get() = _effect.receiveAsFlow()

    fun setEffect(builder: () -> Effect) {
        viewModelScope.launch { _effect.send(builder()) }
    }

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _event.collect { handleEvent(it) }
        }
    }
}