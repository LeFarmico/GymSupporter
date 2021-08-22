package com.lefarmico.donetime.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.core.util.Preconditions
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.CurrentExercisesAdapter
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseName
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseSet
import com.lefarmico.donetime.data.entities.currentExercise.WorkoutData
import com.lefarmico.donetime.databinding.FragmentWorkoutScreenBinding
import com.lefarmico.donetime.viewModels.WorkoutScreenViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class WorkoutScreenFragment : BaseFragment<FragmentWorkoutScreenBinding, WorkoutScreenViewModel>(
    FragmentWorkoutScreenBinding::inflate,
    WorkoutScreenViewModel::class.java
) {

    private var workoutData: WorkoutData = WorkoutData().apply {
        addExercise("Bench Press", "Chest")
        addExercise("Pull up", "Back")
        newSet = { addSet() }
    }

    val adapter = CurrentExercisesAdapter(workoutData)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager
            .setFragmentResultListener(
                REQUEST_KEY,
                this,
                { requestKey, result ->
                    onFragmentResult(requestKey, result)
                }
            )
    }

    override fun setUpViews() {
        binding.listRecycler.adapter = adapter
        binding.addExButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, CategoryListFragment::class.java, null)
                .addToBackStack(BACKSTACK_BRANCH)
                .commit()
        }
        binding.finishButton.setOnClickListener {
            viewModel.putWorkoutNoteToDB(workoutData)
            Toast.makeText(requireContext(), "Your workout saved!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, HomeFragment::class.java, null)
                .commit()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun onFragmentResult(requestKey: String, result: Bundle) {
        Preconditions.checkState(REQUEST_KEY == requestKey)

        val exercise = result.getParcelable<ExerciseName>(KEY_NUMBER)!!
        workoutData.addExercise(exercise.name, exercise.tags)
    }

    private fun addSet(): ExerciseSet {
        return ExerciseSet(100f, 55)
    }

    companion object {
        const val REQUEST_KEY = "exercise_result"
        const val KEY_NUMBER = "key_1"
        const val BACKSTACK_BRANCH = "EXERCISE"
    }
}
