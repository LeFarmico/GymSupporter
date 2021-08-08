package com.lefarmico.donetime.di

import android.content.Context
import androidx.room.Room
import com.lefarmico.donetime.data.ExerciseLibraryRepository
import com.lefarmico.donetime.data.db.AppDataBase
import com.lefarmico.donetime.data.db.dao.ExerciseLibraryDao
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class DataBaseModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Reusable
    fun provideExerciseLibraryDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "exercise_library"
        ).build().itemDao()

    @Provides
    @Reusable
    fun provideExerciseRepository(dao: ExerciseLibraryDao): ExerciseLibraryRepository {
        return ExerciseLibraryRepository(dao)
    }
}
