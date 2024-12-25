package com.example.planner_test_task.feature_calendar.di

import com.example.planner_test_task.core_data.mapper.TaskEntityToTaskMapper
import com.example.planner_test_task.core_data.room.TaskDao
import com.example.planner_test_task.feature_calendar.domain.CalendarUseCase
import com.example.planner_test_task.feature_calendar.domain.CalendarUseCaseImpl
import com.example.planner_test_task.feature_calendar.repository.CalendarRepository
import com.example.planner_test_task.feature_calendar.repository.CalendarRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CalendarModule {

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): CalendarRepository {
        return CalendarRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCase(
        repository: CalendarRepository,
        mapper: TaskEntityToTaskMapper
    ): CalendarUseCase {
        return CalendarUseCaseImpl(repository, mapper)
    }
}
