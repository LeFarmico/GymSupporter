package com.lefarmico.donetime.views.fragments

import com.lefarmico.donetime.adapters.WorkoutAdapter
import com.lefarmico.donetime.data.entities.traning.WorkoutDataBase
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseDataBase
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseMuscleSetEntity
import com.lefarmico.donetime.databinding.FragmentWorkoutScreenBinding
import com.lefarmico.donetime.viewModels.WorkoutScreenViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class WorkoutScreenFragment : BaseFragment<FragmentWorkoutScreenBinding, WorkoutScreenViewModel>(
    FragmentWorkoutScreenBinding::inflate,
    WorkoutScreenViewModel::class.java
) {

    override fun setUpViews() {

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
        val workoutRepo = WorkoutDataBase().apply {
            addExercise(exercise)
            addExercise(exercise2)
            addExButtonEvent = { exercise }
            addSetButtonEvent = {
                ExerciseMuscleSetEntity(25f, 44)
            }
        }

        binding.listRecycler.adapter = WorkoutAdapter(workoutRepo)
    }

    override fun observeView() {
        super.observeView()
    }

    override fun observeData() {
        super.observeData()
    }
}
