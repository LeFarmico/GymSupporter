package com.lefarmico.donetime.di

import com.lefarmico.donetime.data.ExerciseLibraryRepository
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.data.WorkoutNotesRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class DomainModule {
    
    @Provides
    @Reusable
    fun provideInteractor(
        exRepo: ExerciseLibraryRepository,
        workoutRepo: WorkoutNotesRepository
    ): Interactor {
        return Interactor(exRepo, workoutRepo)
    }
}
