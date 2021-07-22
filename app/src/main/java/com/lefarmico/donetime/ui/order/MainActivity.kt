package com.lefarmico.donetime.ui.order

import com.lefarmico.donetime.adapters.exercise.ExerciseAdapter
import com.lefarmico.donetime.adapters.exercise.entity.Exercise
import com.lefarmico.donetime.adapters.workout.WorkoutAdapter
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.donetime.ui.base.BaseActivity

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

        val exAdapter = ExerciseAdapter().apply {
            setExercise(exercise)
        }
        val exAdapter2 = ExerciseAdapter().apply {
            setExercise(exercise2)
        }

        binding.listRecycler.adapter = WorkoutAdapter().apply {
            addExercise(exAdapter)
            addExercise(exAdapter2)
        }
    }

    override fun observeView() {
        super.observeView()
    }

    override fun observeData() {
        super.observeData()
    }
}
