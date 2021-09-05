package com.lefarmico.donetime.views.fragments.listMenu.workout

import androidx.fragment.app.FragmentManager
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.donetime.views.fragments.WorkoutScreenFragment
import com.lefarmico.donetime.views.fragments.listMenu.ExerciseListFragment

class WorkoutExerciseFragment : ExerciseListFragment() {

    override val onItemClickListener: (LibraryDto) -> Unit = {
        it as LibraryDto.Exercise
        setExerciseResult(it.id)
        parentFragmentManager.popBackStack(
            WorkoutScreenFragment.BACKSTACK_BRANCH,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}
