package com.lefarmico.presentation.di.modules

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
import com.lefarmico.presentation.di.scopes.PresentationScope
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule() {

    @Provides
    @PresentationScope
    fun provideExerciseLibraryDao(context: Context): LibraryDao {
        return Room.databaseBuilder(
            context,
            LibraryDataBase::class.java,
            "exercise_library"
        ).build().libraryDao()
    }

    @Provides
    @PresentationScope
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
    @PresentationScope
    fun provideExerciseRepository(dao: LibraryDao): LibraryRepositoryImpl {
        return LibraryRepositoryImpl(dao)
    }

    @Provides
    @PresentationScope
    fun provideWorkoutNotesRepository(dao: WorkoutRecordsDao): WorkoutRecordsRepositoryImpl {
        return WorkoutRecordsRepositoryImpl(dao)
    }

    @Provides
    @PresentationScope
    fun provideCurrentWorkoutRepository(dataBase: CurrentWorkoutDataBase): CurrentWorkoutRepositoryImpl {
        return CurrentWorkoutRepositoryImpl(dataBase)
    }
}
