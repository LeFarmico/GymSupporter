package com.lefarmico.features.di

import com.lefarmico.create_new_exercise.view.CreateNewExerciseFragment
import com.lefarmico.exercise_menu.view.CategoryListFragment
import com.lefarmico.exercise_menu.view.ExerciseListFragment
import com.lefarmico.exercise_menu.view.SubCategoryListFragment
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
    abstract fun provideWorkoutCategoryFragment(): CategoryListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutExerciseFragment(): SubCategoryListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutSubcategoryFragment(): ExerciseListFragment
}
