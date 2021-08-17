package com.lefarmico.donetime.di

import android.content.Context
import androidx.room.Room
import com.lefarmico.donetime.data.ExerciseLibraryRepository
import com.lefarmico.donetime.data.WorkoutNotesRepository
import com.lefarmico.donetime.data.db.LibraryDataBase
import com.lefarmico.donetime.data.db.WorkoutNotesDataBase
import com.lefarmico.donetime.data.db.dao.ExerciseLibraryDao
import com.lefarmico.donetime.data.db.dao.WorkoutNoteDao
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
            LibraryDataBase::class.java,
            "exercise_library"
        ).build().itemDao()

    @Provides
    @Reusable
    fun provideWorkoutNotesDao(context: Context) =
        Room.databaseBuilder(
            context,
            WorkoutNotesDataBase::class.java,
            "workout_notes"
        ).build().itemDao()

    @Provides
    @Reusable
    fun provideExerciseRepository(dao: ExerciseLibraryDao): ExerciseLibraryRepository {
        return ExerciseLibraryRepository(dao)
    }

    @Provides
    @Reusable
    fun provideWorkoutNotesRepository(dao: WorkoutNoteDao): WorkoutNotesRepository {
        return WorkoutNotesRepository(dao)
    }
}
