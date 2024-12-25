package com.example.planner_test_task.feature_task_detail.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.planner_test_task.core_base.BaseScreen
import com.example.planner_test_task.utils.Utils.formatDateForTaskDetail

class TaskDetailScreen : BaseScreen<TaskDetailViewModel, TaskDetailEvent, TaskDetailState, TaskDetailEffect>() {

    @Composable
    override fun Screen(state: TaskDetailState) {
        val taskId = remember { navigation.getTaskIdFromCurrentRoute() }

        LaunchedEffect(taskId) {
            taskId?.let { viewModel.setEvent(TaskDetailEvent.LoadTask(it)) }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            state.task?.let { taskDetail ->
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val (backButton, content) = createRefs()

                    val topGuideline = createGuidelineFromTop(92.dp)

                    IconButton(
                        onClick = { viewModel.setEffect { TaskDetailEffect.NavigateBack } },
                        modifier = Modifier
                            .padding(top = 32.dp, start = 8.dp)
                            .constrainAs(backButton) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }

                    Column(
                        modifier = Modifier
                            .constrainAs(content) {
                                top.linkTo(topGuideline, margin = 12.dp)
                                start.linkTo(parent.start, margin = 12.dp)
                                end.linkTo(parent.end, margin = 12.dp)
                                width = Dimension.fillToConstraints
                            }
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                    ) {
                        Text(
                            text = taskDetail.name,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(8.dp)
                        )

                        Text(
                            text = "Start: ${taskDetail.dateStart.formatDateForTaskDetail()} - End: ${taskDetail.dateFinish.formatDateForTaskDetail()}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(8.dp)
                        )

                        Text(
                            text = taskDetail.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(8.dp)
                        )
                    }
                }
            } ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error loading task")
                }
            }
        }
    }

    @Composable
    override fun OnEvent() {
        val context = LocalContext.current
        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is TaskDetailEffect.ShowError -> {
                        Toast.makeText(
                            context,
                            effect.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is TaskDetailEffect.NavigateBack -> {
                        navigation.navigateBack()
                    }
                }
            }
        }
    }
}
