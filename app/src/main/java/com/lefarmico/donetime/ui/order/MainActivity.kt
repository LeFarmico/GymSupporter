package com.lefarmico.donetime.ui.order

import android.widget.Toast
import com.lefarmico.donetime.adapters.exercise.ExerciseAdapter
import com.lefarmico.donetime.adapters.exercise.ExerciseMenuFactory
import com.lefarmico.donetime.adapters.exercise.entity.Exercise
import com.lefarmico.donetime.adapters.exercise.entity.ExerciseName
import com.lefarmico.donetime.adapters.exercise.entity.ExerciseSet
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.donetime.ui.base.BaseActivity
import com.lefarmico.lerecycle.extractValues

class MainActivity : BaseActivity<ActivityMainBinding, OrderViewModel>(
    { ActivityMainBinding.inflate(it) },
    OrderViewModel::class.java
) {
    override fun setUpViews() {
        binding.listRecycler.adapter = ExerciseAdapter().apply {
            setItemTypes(
                extractValues<ExerciseMenuFactory>()
            )
            val exercise = Exercise("Bench press").apply {
                addSet(50f, 20)
                addSet(80f, 10)
            }
            val exercise2 = Exercise("Bench press").apply {
                addSet(50f, 20)
                addSet(50f, 20)
                addSet(80f, 10)
            }
            addExercise(exercise)
            addExercise(exercise2)
            setItems()
            setOnClickEvent {
                when (it.type) {
                    ExerciseMenuFactory.EXERCISE -> {
                        val exercise = it as ExerciseName
                        Toast.makeText(this@MainActivity, "Ex ${exercise.name}", Toast.LENGTH_SHORT).show()
                    }
                    ExerciseMenuFactory.SET -> {
                        val date = it as ExerciseSet
                        Toast.makeText(this@MainActivity, "Ex ${date.setNumber}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun observeView() {
        super.observeView()
    }

    override fun observeData() {
        super.observeData()
    }
}
