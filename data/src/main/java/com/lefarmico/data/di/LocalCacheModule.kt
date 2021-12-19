package com.lefarmico.data.di

import com.lefarmico.data.db.FormatterCache
import com.lefarmico.data.db.LocalDateTimeCache
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
    fun provideLocalDateTimeCache(): LocalDateTimeCache {
        return LocalDateTimeCache()
    }
}
