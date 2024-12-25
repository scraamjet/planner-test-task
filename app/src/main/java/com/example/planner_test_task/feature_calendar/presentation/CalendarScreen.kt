package com.example.planner_test_task.feature_calendar.presentation

import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.planner_test_task.R
import com.example.planner_test_task.core_base.BaseScreen
import com.example.planner_test_task.core_model.Task
import com.example.planner_test_task.utils.Utils.formatDateTaskItem
import java.util.Calendar

class CalendarScreen : BaseScreen<CalendarViewModel, CalendarEvent, CalendarState, CalendarEffect>() {

    @Composable
    override fun Screen(state: CalendarState) {
        val tasks = state.tasks
        val selectedDate = state.selectedDate

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val (calendarView, progressBar, emptyMessage, taskList, createButton) = createRefs()

            val topGuideline = createGuidelineFromTop(400.dp)
            val bottomGuideline = createGuidelineFromBottom(92.dp)

            AndroidView(
                factory = { context ->
                    CalendarView(context).apply {
                        date = selectedDate
                        setOnDateChangeListener { _, year, month, dayOfMonth ->
                            val calendar = Calendar.getInstance().apply {
                                set(year, month, dayOfMonth)
                            }
                            viewModel.setEvent(CalendarEvent.SelectDate(calendar.timeInMillis))
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .constrainAs(calendarView) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(topGuideline)
                        verticalBias = 0.5f
                    }
            )

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(progressBar) {
                        top.linkTo(topGuideline)
                        bottom.linkTo(bottomGuideline)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        verticalBias = 0.5f
                    }
                )
            } else if (tasks.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_tasks_message),
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(emptyMessage) {
                            top.linkTo(topGuideline)
                            bottom.linkTo(bottomGuideline)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            verticalBias = 0.5f
                        }
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                        .constrainAs(taskList) {
                            top.linkTo(topGuideline)
                            bottom.linkTo(bottomGuideline)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            verticalBias = 0.5f
                        }
                ) {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onClick = { viewModel.setEvent(CalendarEvent.TaskClick(task.id)) }
                        )
                    }
                }
            }

            Button(
                onClick = { viewModel.setEvent(CalendarEvent.CreateTaskClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(createButton) {
                        top.linkTo(bottomGuideline)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        verticalBias = 0.5f
                    }
            ) {
                Text(text = stringResource(R.string.create_task_button), color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }



    @Composable
    override fun OnEvent() {
        val context = LocalContext.current
        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is CalendarEffect.NavigateToCreateTask -> {
                        navigation.navigateToCreateTask()
                    }

                    is CalendarEffect.NavigateToTaskDetail -> {
                        navigation.navigateToTaskDetail(effect.taskId)
                    }

                    is CalendarEffect.ShowErrorToast -> {
                        Toast.makeText(
                            context,
                            effect.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(Unit) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.setEvent(CalendarEvent.SelectDate(viewModel.currentState.selectedDate))
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }


    @Composable
    fun TaskItem(task: Task, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = task.name, style = typography.headlineMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Start: ${task.dateStart.formatDateTaskItem()}",
                    style = typography.bodyMedium
                )
                Text(
                    text = "End: ${task.dateFinish.formatDateTaskItem()}",
                    style = typography.bodyMedium
                )
            }
        }
    }
}