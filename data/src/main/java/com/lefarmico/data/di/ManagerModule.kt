package com.lefarmico.data.di

import com.lefarmico.data.repository.DateTimeManagerImpl
import com.lefarmico.data.repository.FormatterManagerImpl
import com.lefarmico.domain.repository.DateTimeManager
import com.lefarmico.domain.repository.FormatterManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun provideCalendarManager(repo: DateTimeManagerImpl): DateTimeManager

    @Binds
    @Singleton
    abstract fun provideFormatterManager(manager: FormatterManagerImpl): FormatterManager
}
