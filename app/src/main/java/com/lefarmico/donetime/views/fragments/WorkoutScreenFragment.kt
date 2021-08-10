package com.lefarmico.donetime.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.core.util.Preconditions
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.WorkoutAdapter
import com.lefarmico.donetime.data.entities.traning.AddExerciseEntity
import com.lefarmico.donetime.data.entities.traning.WorkoutDataBase
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseDataBase
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseMuscleSetEntity
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseNameEntity
import com.lefarmico.donetime.databinding.FragmentWorkoutScreenBinding
import com.lefarmico.donetime.viewModels.WorkoutScreenViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class WorkoutScreenFragment : BaseFragment<FragmentWorkoutScreenBinding, WorkoutScreenViewModel>(
    FragmentWorkoutScreenBinding::inflate,
    WorkoutScreenViewModel::class.java
) {

    lateinit var adapter: WorkoutAdapter
    var workoutRepo: WorkoutDataBase = WorkoutDataBase()

    val exercise = ExerciseDataBase().apply {
        setNameAndTags("Bench press", "Chest")
        addSet(50f, 20)
        addSet(80f, 10)
    }
    val exercise2 = ExerciseDataBase().apply {
        setNameAndTags("Bench press", "Chest")
        addSet(50f, 20)
        addSet(80f, 10)
    }

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
        Log.d("XXX", this.hashCode().toString())
        workoutRepo.apply {
            addExercise(exercise)
            addExercise(exercise2)
            addSetButtonEvent = {
                ExerciseMuscleSetEntity(25f, 44)
            }
            addExerciseButton = AddExerciseEntity {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment, CategoryListFragment::class.java, null)
                    .addToBackStack("EX")
                    .commit()
            }
        }
        binding.listRecycler.adapter = WorkoutAdapter(workoutRepo)
    }

    @SuppressLint("RestrictedApi")
    private fun onFragmentResult(requestKey: String, result: Bundle) {
        Preconditions.checkState(REQUEST_KEY == requestKey)

        val exercise = result.getParcelable<ExerciseNameEntity>(KEY_NUMBER)!!
        workoutRepo.addExercise(
            ExerciseDataBase().apply {
                setNameAndTags(exercise.name, exercise.tags)
            }
        )
    }

    companion object {
        const val REQUEST_KEY = "exercise_result"
        const val KEY_NUMBER = "key_1"
    }
}
