package com.lefarmico.donetime.di

import com.lefarmico.donetime.data.ExerciseLibraryRepository
import com.lefarmico.donetime.data.Interactor
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class DomainModule {
    
    @Provides
    @Reusable
    fun provideInteractor(repo: ExerciseLibraryRepository): Interactor {
        return Interactor(repo)
    }
}
