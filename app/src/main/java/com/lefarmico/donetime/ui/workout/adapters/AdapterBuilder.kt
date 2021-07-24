package com.lefarmico.donetime.ui.workout.adapters

import com.lefarmico.donetime.ui.workout.adapters.exercise.ExerciseAdapter
import com.lefarmico.donetime.ui.workout.data.Exercise

class AdapterBuilder {
    
    fun createExerciseAdapter(exercise: Exercise, isActive: Boolean): ExerciseAdapter {
        return ExerciseAdapter().apply { 
            setExercise(exercise)
            setActive(isActive)
        }
    }
    
    fun createExerciseAdapters(exerciseList: List<Exercise>, activePosition: Int) {
        exerciseList.forEachIndexed { index, exercise ->
            if (index == activePosition) {
                createExerciseAdapter(exercise, true)
            } else {
                createExerciseAdapter(exercise, false)
            }
        }
    }
}
