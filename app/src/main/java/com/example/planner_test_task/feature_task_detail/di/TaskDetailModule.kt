package com.example.planner_test_task.feature_task_detail.di

import com.example.planner_test_task.core_data.mapper.TaskEntityToTaskMapper
import com.example.planner_test_task.core_data.room.TaskDao
import com.example.planner_test_task.feature_task_detail.domain.TaskDetailUseCase
import com.example.planner_test_task.feature_task_detail.domain.TaskDetailUseCaseImpl
import com.example.planner_test_task.feature_task_detail.repository.TaskDetailRepository
import com.example.planner_test_task.feature_task_detail.repository.TaskDetailRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskDetailModule {

    @Provides
    @Singleton
    fun provideTaskDetailRepository(taskDao: TaskDao): TaskDetailRepository {
        return TaskDetailRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskDetailUseCase(
        repository: TaskDetailRepository,
        mapper: TaskEntityToTaskMapper
    ): TaskDetailUseCase {
        return TaskDetailUseCaseImpl(repository, mapper)
    }
}
