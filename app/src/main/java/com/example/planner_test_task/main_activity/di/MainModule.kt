package com.example.planner_test_task.main_activity.di

import com.example.planner_test_task.core_data.json.JsonDataSource
import com.example.planner_test_task.core_data.room.TaskDao
import com.example.planner_test_task.main_activity.domain.MainUseCase
import com.example.planner_test_task.main_activity.domain.MainUseCaseImpl
import com.example.planner_test_task.main_activity.repository.MainRepository
import com.example.planner_test_task.main_activity.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        jsonDataSource: JsonDataSource,
        taskDao: TaskDao
    ): MainRepository = MainRepositoryImpl(jsonDataSource, taskDao)

    @Provides
    @Singleton
    fun provideMainUseCase(
        mainRepository: MainRepository
    ): MainUseCase = MainUseCaseImpl(mainRepository)
}
