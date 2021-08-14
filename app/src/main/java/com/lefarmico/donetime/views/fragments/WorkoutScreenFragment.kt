package com.lefarmico.donetime.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.util.Preconditions
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.WorkoutAdapter
import com.lefarmico.donetime.data.entities.traning.WorkoutDataBase
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseData
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseMuscleSetEntity
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseNameEntity
import com.lefarmico.donetime.data.entities.traning.exercise.ISetEntity
import com.lefarmico.donetime.databinding.FragmentWorkoutScreenBinding
import com.lefarmico.donetime.viewModels.WorkoutScreenViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class WorkoutScreenFragment : BaseFragment<FragmentWorkoutScreenBinding, WorkoutScreenViewModel>(
    FragmentWorkoutScreenBinding::inflate,
    WorkoutScreenViewModel::class.java
) {

    val exercise = ExerciseData("Bench press", "Chest")
    val exercise2 = ExerciseData("Pull ups", "Back")

    var workoutRepo: WorkoutDataBase = WorkoutDataBase().apply {
        addExercise(exercise)
        addExercise(exercise2)
        buttonEventAddSet = { addSetButtonListener() }
    }
    var adapter: WorkoutAdapter = WorkoutAdapter(workoutRepo)

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
        workoutRepo.apply {
            buttonEventAddSet = {
                ExerciseMuscleSetEntity(25f, 44)
            }
        }
        binding.listRecycler.adapter = adapter
        binding.addExButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, CategoryListFragment::class.java, null)
                .addToBackStack(BACKSTACK_BRANCH)
                .commit()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun onFragmentResult(requestKey: String, result: Bundle) {
        Preconditions.checkState(REQUEST_KEY == requestKey)

        val exercise = result.getParcelable<ExerciseNameEntity>(KEY_NUMBER)!!
        workoutRepo.addExercise(
            ExerciseData(exercise.name, exercise.tags)
        )
    }

    private fun addSetButtonListener(): ISetEntity {
        return ExerciseMuscleSetEntity(100f, 55)
    }

    companion object {
        const val REQUEST_KEY = "exercise_result"
        const val KEY_NUMBER = "key_1"
        const val BACKSTACK_BRANCH = "EXERCISE"
    }
}
