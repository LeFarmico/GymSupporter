package com.lefarmico.presentation.di.modules

import android.content.Context
import androidx.room.Room
import com.lefarmico.data.db.CurrentWorkoutDataBase
import com.lefarmico.data.db.LibraryDataBase
import com.lefarmico.data.db.WorkoutRecordsDataBase
import com.lefarmico.data.db.dao.LibraryDao
import com.lefarmico.data.db.dao.WorkoutRecordsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideExerciseLibraryDao(context: Context): LibraryDao {
        return Room.databaseBuilder(
            context,
            LibraryDataBase::class.java,
            "exercise_library"
        ).build().libraryDao()
    }

    @Provides
    @Singleton
    fun provideWorkoutNotesDao(context: Context): WorkoutRecordsDao {
        return Room.databaseBuilder(
            context,
            WorkoutRecordsDataBase::class.java,
            "workout_records"
        ).build().itemDao()
    }

    @Provides
    fun provideCurrentWorkoutDataBase(): CurrentWorkoutDataBase {
        return CurrentWorkoutDataBase()
    }
}
