package com.lefarmico.features.di

import com.lefarmico.create_new_exercise.view.CreateNewExerciseFragment
import com.lefarmico.detailed_record_workout.view.DetailedWorkoutRecordFragment
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

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun provideAddExerciseFragment(): CreateNewExerciseFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutScreenFragment(): WorkoutScreenFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun provideExerciseDetailsFragment(): ExerciseDetailsFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutCategoryFragment(): CategoryListFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutExerciseFragment(): SubCategoryListFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutSubcategoryFragment(): ExerciseListFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun provideEditWorkoutRecordFragment(): DetailedWorkoutRecordFragment
}
