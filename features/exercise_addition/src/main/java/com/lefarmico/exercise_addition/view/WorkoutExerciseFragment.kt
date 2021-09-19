package com.lefarmico.exercise_addition.view

import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.exercise_menu.view.ExerciseListFragment

class WorkoutExerciseFragment : ExerciseListFragment() {

    override val onItemClickListener: (LibraryDto) -> Unit = {
        it as LibraryDto.Exercise
        setExerciseResult(it.id)
        // TODO navigation module
//        parentFragmentManager.popBackStack(
//            com.lefarmico.workout.view.WorkoutScreenFragment.BACKSTACK_BRANCH,
//            FragmentManager.POP_BACK_STACK_INCLUSIVE
//        )
    }
}
