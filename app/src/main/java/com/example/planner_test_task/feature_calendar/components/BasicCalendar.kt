package com.example.planner_test_task.feature_calendar.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@Composable
fun BasicCalendar(
    modifier: Modifier = Modifier,
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
) {
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedLocalDate = remember { mutableStateOf(Instant.ofEpochMilli(selectedDate).atZone(ZoneId.systemDefault()).toLocalDate()) }
    val daysInMonth by derivedStateOf {
        YearMonth.of(currentDate.value.year, currentDate.value.month).lengthOfMonth()
    }
    val daysOfWeek = DayOfWeek.entries.toTypedArray().let { listOf(it.last()) + it.dropLast(1) } // Start week on Sunday
        .map { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Header(
            currentDate = currentDate.value,
            onPreviousMonth = { currentDate.value = currentDate.value.minusMonths(1) },
            onNextMonth = { currentDate.value = currentDate.value.plusMonths(1) }
        )

        DaysOfWeekRow(daysOfWeek)

        DaysGrid(
            currentDate = currentDate.value,
            selectedDate = selectedLocalDate.value,
            daysInMonth = daysInMonth,
            onDateSelected = { date ->
                selectedLocalDate.value = date
                onDateSelected(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000)
            }
        )
    }
}

@Composable
fun Header(
    currentDate: LocalDate,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous month")
        }
        Text(
            color = MaterialTheme.colorScheme.primary,
            text = "${currentDate.month} ${currentDate.year}",
            style = MaterialTheme.typography.headlineMedium
        )
        IconButton(onClick = onNextMonth) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next month")
        }
    }
}

@Composable
fun DaysOfWeekRow(daysOfWeek: List<String>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        daysOfWeek.forEach { day ->
            Text(text = day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun DaysGrid(
    currentDate: LocalDate,
    selectedDate: LocalDate,
    daysInMonth: Int,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = LocalDate.of(currentDate.year, currentDate.month, 1)
    val firstDayOfWeek = (firstDayOfMonth.dayOfWeek.value % 7)
    val totalSlots = daysInMonth + firstDayOfWeek

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(totalSlots) { index ->
            if (index < firstDayOfWeek) {
                Spacer(modifier = Modifier.size(40.dp))
            } else {
                val day = index - firstDayOfWeek + 1
                val date = LocalDate.of(currentDate.year, currentDate.month, day)
                DayItem(
                    day = day,
                    isSelected = date == selectedDate,
                    isToday = date == LocalDate.now(),
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }
}


@Composable
fun DayItem(
    modifier: Modifier = Modifier,
    day: Int,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor by animateColorAsState(
        targetValue = when {
            isSelected -> MaterialTheme.colorScheme.primary
            isToday -> MaterialTheme.colorScheme.surfaceVariant
            else -> Color.Transparent
        }, label = ""
    )

    val textColor = MaterialTheme.colorScheme.contentColorFor(backgroundColor)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .background(backgroundColor, CircleShape)
            .clickable(onClick = onClick)
            .semantics {
                contentDescription = "Day $day" +
                        if (isToday) ", Today" else "" +
                                if (isSelected) ", Selected" else ""
            }
    ) {
        Text(
            text = day.toString(),
            color = textColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
