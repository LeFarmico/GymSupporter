package com.lefarmico.donetime.di

import android.content.Context
import androidx.room.Room
import com.lefarmico.data.db.CurrentWorkoutDataBase
import com.lefarmico.data.db.LibraryDataBase
import com.lefarmico.data.db.WorkoutRecordsDataBase
import com.lefarmico.data.db.dao.LibraryDao
import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.repository.CurrentWorkoutRepositoryImpl
import com.lefarmico.data.repository.LibraryRepositoryImpl
import com.lefarmico.data.repository.WorkoutRecordsRepositoryImpl
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
    fun provideExerciseLibraryDao(context: Context): LibraryDao {
        return Room.databaseBuilder(
            context,
            LibraryDataBase::class.java,
            "exercise_library"
        ).build().libraryDao()
    }

    @Provides
    @Reusable
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

    @Provides
    @Reusable
    fun provideExerciseRepository(dao: LibraryDao): LibraryRepositoryImpl {
        return LibraryRepositoryImpl(dao)
    }

    @Provides
    @Reusable
    fun provideWorkoutNotesRepository(dao: WorkoutRecordsDao): WorkoutRecordsRepositoryImpl {
        return WorkoutRecordsRepositoryImpl(dao)
    }

    @Provides
    @Reusable
    fun provideCurrentWorkoutRepository(dataBase: CurrentWorkoutDataBase): CurrentWorkoutRepositoryImpl {
        return CurrentWorkoutRepositoryImpl(dataBase)
    }
}
