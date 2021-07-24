package com.lefarmico.donetime.ui.main

import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.donetime.ui.base.BaseActivity
import com.lefarmico.donetime.ui.workout.adapters.workout.WorkoutAdapter
import com.lefarmico.donetime.ui.workout.data.Exercise
import com.lefarmico.donetime.ui.workout.data.WorkoutRepository

class MainActivity : BaseActivity<ActivityMainBinding, OrderViewModel>(
    { ActivityMainBinding.inflate(it) },
    OrderViewModel::class.java
) {
    override fun setUpViews() {

        val exercise = Exercise().apply {
            setNameAndTags("Bench press", "Chest")
            addSet(50f, 20)
            addSet(80f, 10)
        }
        val exercise2 = Exercise().apply {
            setNameAndTags("Bench press", "Chest")
            addSet(50f, 20)
            addSet(80f, 10)
        }
        val workoutRepo = WorkoutRepository().apply {
            addExercise(exercise)
            addExercise(exercise2)
        }
//        val exAdapter = ExerciseAdapter().apply {
//            setExercise(exercise)
//        }
//        val exAdapter2 = ExerciseAdapter().apply {
//            setExercise(exercise2)
//        }

        binding.listRecycler.adapter = WorkoutAdapter(workoutRepo).apply {
        }
    }

    override fun observeView() {
        super.observeView()
    }

    override fun observeData() {
        super.observeData()
    }
}
