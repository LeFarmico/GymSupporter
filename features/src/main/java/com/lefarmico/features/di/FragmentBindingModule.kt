package com.lefarmico.features.di

import com.lefarmico.create_new_exercise.view.CreateNewExerciseFragment
import com.lefarmico.exercise_addition.view.WorkoutCategoryFragment
import com.lefarmico.exercise_addition.view.WorkoutExerciseFragment
import com.lefarmico.exercise_addition.view.WorkoutSubcategoryFragment
import com.lefarmico.exercise_library.view.LibraryCategoryFragment
import com.lefarmico.exercise_library.view.LibraryExerciseFragment
import com.lefarmico.exercise_library.view.LibrarySubCategoryFragment
import com.lefarmico.home.view.HomeFragment
import com.lefarmico.workout.view.WorkoutScreenFragment
import com.lefarmico.workout_exercise_addition.view.ExerciseDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideAddExerciseFragment(): CreateNewExerciseFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutScreenFragment(): WorkoutScreenFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideExerciseDetailsFragment(): ExerciseDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideLibraryCategoryFragment(): LibraryCategoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideLibraryExerciseFragment(): LibraryExerciseFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideLibrarySubCategoryFragment(): LibrarySubCategoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutCategoryFragment(): WorkoutCategoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutExerciseFragment(): WorkoutExerciseFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutSubcategoryFragment(): WorkoutSubcategoryFragment
}
