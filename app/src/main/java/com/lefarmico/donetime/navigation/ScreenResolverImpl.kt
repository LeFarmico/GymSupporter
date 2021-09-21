package com.lefarmico.donetime.navigation

import android.os.Parcelable
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.lefarmico.create_new_exercise.view.CreateNewExerciseFragment
import com.lefarmico.donetime.BuildConfig
import com.lefarmico.donetime.R
import com.lefarmico.exercise_menu.view.CategoryListFragment
import com.lefarmico.exercise_menu.view.ExerciseListFragment
import com.lefarmico.exercise_menu.view.SubCategoryListFragment
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.navigation.screen.ScreenResolver
import com.lefarmico.workout_exercise_addition.view.ExerciseDetailsFragment
import java.lang.IllegalStateException
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
                    R.id.fragment_home,
                    null,
                    null,
                    navExtras
                )
            }
            Screen.WORKOUT_SCREEN -> {
                navController?.navigate(
                    R.id.fragment_workout,
                    null,
                    null,
                    navExtras
                )
            }
//            Screen.SETTINGS_SCREEN -> {
//                navController?.navigate(
//                    R.id.fragment,
//                    null,
//                null,
//                    navExtras
//                )
//            }
            Screen.CREATE_NEW_EXERCISE_SCREEN -> {
                navController?.navigate(
                    R.id.fragment_create_new_exercise,
                    CreateNewExerciseFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.CATEGORY_SCREEN_FROM_LIBRARY -> {
                navController?.navigate(
                    R.id.fragment_categories,
                    CategoryListFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.SUBCATEGORY_SCREEN_FROM_LIBRARY -> {
                navController?.navigate(
                    R.id.fragment_subcategories,
                    SubCategoryListFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.EXERCISE_SCREEN_FROM_LIBRARY -> {
                navController?.navigate(
                    R.id.fragment_exercises,
                    ExerciseListFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
            Screen.EXERCISE_DETAILS_SCREEN_FROM_LIBRARY -> {
                navController?.navigate(
                    R.id.fragment_exercise_details,
                    ExerciseDetailsFragment.createBundle(data),
                    null,
                    navExtras
                )
            }
//            Screen.EXERCISE_DETAILS_SCREEN_FROM_WORKOUT -> {
//                navController.navigate(
//                    R.id.fragment,
//                    ,
//                null,
//                    navExtras
//                )
//            }

            Screen.SET_PARAMETERS_DIALOG -> {}
            else -> {
                if (BuildConfig.DEBUG) {
                    throw (IllegalStateException("Screen is not exist $this"))
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
