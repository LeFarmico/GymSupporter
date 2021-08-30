package com.lefarmico.donetime.views.fragments.listMenu.workout

import androidx.fragment.app.FragmentManager
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseName
import com.lefarmico.donetime.data.entities.library.ILibraryItem
import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.views.fragments.WorkoutScreenFragment
import com.lefarmico.donetime.views.fragments.listMenu.ExerciseListFragment

class WorkoutExerciseFragment : ExerciseListFragment() {

    override val onItemClickListener: (ILibraryItem) -> Unit = {
        it as LibraryExercise
        setExerciseResult(ExerciseName(it.title, it.title))
        parentFragmentManager.popBackStack(
            WorkoutScreenFragment.BACKSTACK_BRANCH,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}
