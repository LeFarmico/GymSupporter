package com.lefarmico.donetime.navigation

import android.os.Parcelable
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.lefarmico.create_new_exercise.CreateExerciseFragment
import com.lefarmico.detailed_record_workout.DetailedWorkoutRecordFragment
import com.lefarmico.donetime.BuildConfig
import com.lefarmico.donetime.R
import com.lefarmico.exercise_menu.view.CategoryFragment
import com.lefarmico.exercise_menu.view.ExerciseListFragment
import com.lefarmico.exercise_menu.view.SubcategoryFragment
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.navigation.screen.ScreenResolver
import com.lefarmico.workout.WorkoutFragment
import com.lefarmico.workout_exercise_addition.ExerciseDetailsFragment
import javax.inject.Inject

class ScreenResolverImpl @Inject constructor() : ScreenResolver {
    override fun navigate(
        navController: NavController?,
        screen: Screen,
        data: Parcelable?,
        sharedElements: Map<Any, String>?
    ) {

        val navExtras = toNavExtras(sharedElements)

        when (screen) {
            Screen.HOME_SCREEN -> {
                navController?.navigate(
                    R.id.action_workoutScreenFragment_to_navigation_home2,
                    null,
                    null,
                    navExtras
                )
            }
            Screen.WORKOUT_SCREEN -> {
                navController?.navigate(
                    R.id.action_homeFragment_to_workoutScreenFragment,
                    WorkoutFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.SETTINGS_SCREEN -> {
                navController?.navigate(
                    R.id.action_navigation_home_to_settingsScreenFragment,
                    null,
                    null,
                    navExtras
                )
            }
            Screen.CREATE_NEW_EXERCISE_SCREEN -> {
                navController?.navigate(
                    R.id.action_exerciseListFragment_to_createNewExerciseFragment,
                    CreateExerciseFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.CATEGORY_LIST_SCREEN -> {
                navController?.navigate(
                    R.id.action_workoutScreenFragment_to_categoryListFragment,
                    CategoryFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.SUBCATEGORY_LIST_SCREEN -> {
                navController?.navigate(
                    R.id.action_categoryListFragment_to_subCategoryListFragment,
                    SubcategoryFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.EXERCISE_LIST_SCREEN -> {
                navController?.navigate(
                    R.id.action_subCategoryListFragment_to_exerciseListFragment,
                    ExerciseListFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.EXERCISE_DETAILS_SCREEN -> {
                navController?.navigate(
                    R.id.action_exerciseListFragment_to_exerciseDetailsFragment,
                    ExerciseDetailsFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.EXERCISE_DETAILS_SCREEN_FROM_WORKOUT -> {
                navController?.navigate(
                    R.id.action_workoutScreenFragment_to_exerciseDetailsFragment,
                    ExerciseDetailsFragment.createBundle(data),
                    null,
                    navExtras
                )
            }

            Screen.SET_PARAMETERS_DIALOG -> {}
            Screen.ACTION_ADD_EXERCISE_TO_WORKOUT_SCREEN -> {
                navController?.navigate(
                    R.id.action_exerciseListFragment_to_workoutScreenFragment,
                    WorkoutFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.EDIT_WORKOUT_RECORD_SCREEN -> {
                navController?.navigate(
                    R.id.action_navigation_home_to_editWorkoutRecordFragment2,
                    DetailedWorkoutRecordFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.WORKOUT_SCREEN_FROM_DETAILED_SCREEN -> {
                navController?.navigate(
                    R.id.action_editWorkoutRecordFragment_to_workoutScreenFragment,
                    WorkoutFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            else -> {
                if (BuildConfig.DEBUG) {
                    throw (IllegalArgumentException("Screen is not exist $this"))
                }
            }
        }
    }

    private fun toNavExtras(sharedElements: Map<Any, String>?): Navigator.Extras {
        return FragmentNavigator.Extras.Builder().apply {
            sharedElements?.forEach { (key, value) ->
                (key as? View)?.let { view ->
                    addSharedElement(view, value)
                }
            }
        }.build()
    }
}
