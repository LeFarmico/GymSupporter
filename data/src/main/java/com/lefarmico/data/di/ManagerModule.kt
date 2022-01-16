package com.lefarmico.data.di

import com.lefarmico.data.repository.manager.*
import com.lefarmico.domain.repository.manager.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun provideCalendarManager(repo: DateManagerImpl): DateManager

    @Binds
    @Singleton
    abstract fun provideFormatterManager(manager: FormatterManagerImpl): FormatterManager

    @Binds
    @Singleton
    abstract fun provideFormatterMonthManager(manager: FormatterMonthManagerImpl): FormatterMonthManager

    @Binds
    @Singleton
    abstract fun provideFormatterTimeManagerManager(
        manager: FormatterTimeManagerImpl
    ): FormatterTimeManager

    @Binds
    @Singleton
    abstract fun provideWorkoutTimeSwitchScheduleManager(
        manager: TimeScheduleManagerImpl
    ): TimeScheduleManager

    @Binds
    @Singleton
    abstract fun provideWorkoutTitleManager(manager: WorkoutTitleManagerImpl): WorkoutTitleManager

    @Binds
    @Singleton
    abstract fun provideRemindTimeManager(manager: RemindTimeManagerImpl): RemindTimeManager
}
