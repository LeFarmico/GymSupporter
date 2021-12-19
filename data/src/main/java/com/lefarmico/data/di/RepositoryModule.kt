package com.lefarmico.data.di

import com.lefarmico.data.repository.CurrentWorkoutRepositoryImpl
import com.lefarmico.data.repository.LibraryRepositoryImpl
import com.lefarmico.data.repository.WorkoutRecordsRepositoryImpl
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideExerciseRepository(repo: LibraryRepositoryImpl): LibraryRepository

    @Binds
    @Singleton
    abstract fun provideWorkoutNotesRepository(repo: WorkoutRecordsRepositoryImpl): WorkoutRecordsRepository

    @Binds
    @Singleton
    abstract fun provideCurrentWorkoutRepository(repo: CurrentWorkoutRepositoryImpl): CurrentWorkoutRepository
}
