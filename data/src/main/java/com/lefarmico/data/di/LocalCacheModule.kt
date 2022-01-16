package com.lefarmico.data.di

import com.lefarmico.data.db.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalCacheModule {

    @Provides
    @Singleton
    fun provideFormatterCache(): FormatterCache {
        return FormatterCache()
    }

    @Provides
    @Singleton
    fun provideFormatterMonthCache(): FormatterMonthCache {
        return FormatterMonthCache()
    }

    @Provides
    @Singleton
    fun provideLocalDateTimeCache(): LocalDateCache {
        return LocalDateCache()
    }

    @Provides
    @Singleton
    fun provideLocalTimeCache(): WorkoutSwitchTimeCache {
        return WorkoutSwitchTimeCache()
    }

    @Provides
    @Singleton
    fun provideFormatterTimeCache(): FormatterTimeCache {
        return FormatterTimeCache()
    }

    @Provides
    @Singleton
    fun provideRemindTimeCache(): RemindTimeCache {
        return RemindTimeCache()
    }
    @Provides
    @Singleton
    fun provideWorkoutTitleCache(): WorkoutTitleCache {
        return WorkoutTitleCache()
    }
}
