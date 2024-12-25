package com.example.planner_test_task.feature_create_task.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.planner_test_task.R
import com.example.planner_test_task.core_base.BaseScreen
import com.example.planner_test_task.feature_create_task.components.CustomCalendar

class CreateTaskScreen : BaseScreen<CreateTaskViewModel, CreateTaskEvent, CreateTaskState, CreateTaskEffect>() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Screen(state: CreateTaskState) {
        var dropdownExpanded by remember { mutableStateOf(false) }
        var selectedTime by remember { mutableStateOf(timeSlots[0]) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            IconButton(
                onClick = { viewModel.setEffect { CreateTaskEffect.NavigateBack } },
                modifier = Modifier.padding(top =32.dp, start = 8.dp)
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.setEvent(CreateTaskEvent.NameChanged(it)) },
                label = { Text(stringResource(R.string.task_name_label)) },
                isError = state.name.length < 3,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            if (state.name.length !in 3..22) {
                Text(
                    stringResource(R.string.name_error),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.setEvent(CreateTaskEvent.DescriptionChanged(it)) },
                label = { Text(stringResource(R.string.task_description_label)) },
                isError = state.description.length < 5,
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            if (state.description.length !in 5..80) {
                Text(
                    stringResource(R.string.description_error),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            CustomCalendar(
                selectedDate = state.selectedDate,
                onDateSelected = {
                    viewModel.setEvent(CreateTaskEvent.DateChanged(it))
                },
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                TextField(
                    readOnly = true,
                    value = selectedTime,
                    onValueChange = {},
                    label = { Text("Time") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = dropdownExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    timeSlots.forEachIndexed { index, timeSlot ->
                        DropdownMenuItem(
                            text = { Text(timeSlot) },
                            onClick = {
                                selectedTime = timeSlots[index]
                                viewModel.setEvent(CreateTaskEvent.StartTimeChanged(timeSlot.substringBefore("-")))
                                viewModel.setEvent(CreateTaskEvent.EndTimeChanged(timeSlot.substringAfter("-")))
                                dropdownExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.setEvent(CreateTaskEvent.CreateTaskClicked) },
                enabled = state.isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(stringResource(R.string.create_task_button))
            }
        }
    }

    @Composable
    override fun OnEvent() {
        val context = LocalContext.current
        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is CreateTaskEffect.TaskCreated -> {
                        navigation.navigateBack()
                        Toast.makeText(context, "Task created successfully", Toast.LENGTH_SHORT).show()
                    }
                    is CreateTaskEffect.ShowErrorToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }

                    is CreateTaskEffect.NavigateBack -> {
                        navigation.navigateBack()
                    }
                }
            }
        }
    }

    companion object {
        val timeSlots = listOf(
            "00:00-01:00", "01:00-02:00", "02:00-03:00", "03:00-04:00",
            "04:00-05:00", "05:00-06:00", "06:00-07:00", "07:00-08:00",
            "08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00",
            "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00",
            "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00",
            "20:00-21:00", "21:00-22:00", "22:00-23:00", "23:00-00:00"
        )
    }
}
