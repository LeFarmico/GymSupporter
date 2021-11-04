package com.lefarmico.data.db

import com.lefarmico.data.db.entity.CurrentWorkoutData
import java.lang.NullPointerException

class CurrentWorkoutDataBase {

    private val exerciseWithSetsList = mutableListOf<CurrentWorkoutData.ExerciseWithSets>()
    private var exerciseId = 1

    fun insertExercise(exercise: CurrentWorkoutData.ExerciseWithSets): Long {
        val currentExercise = CurrentWorkoutData.Exercise(
            id = exerciseId,
            libraryId = exercise.exercise.libraryId,
            title = exercise.exercise.title
        )

        exerciseWithSetsList.add(
            CurrentWorkoutData.ExerciseWithSets(
                currentExercise,
                exercise.setList
            )
        )
        return exerciseId++.toLong()
    }

    fun getExercises(): List<CurrentWorkoutData.ExerciseWithSets> {
        return exerciseWithSetsList
    }

    fun getExercise(exerciseId: Int): CurrentWorkoutData.ExerciseWithSets? {
        return exerciseWithSetsList.find { it.exercise.id == exerciseId }
    }

    fun getSets(exerciseId: Int): List<CurrentWorkoutData.Set>? {
        val exercise = exerciseWithSetsList.find { it.exercise.id == exerciseId }
        return exercise?.setList
    }

    fun deleteExercise(exerciseId: Int): Long? = actionResolver {
        val exerciseWithSets = exerciseWithSetsList.find { it.exercise.id == exerciseId }
        exerciseWithSetsList.remove(exerciseWithSets)
        exerciseId.toLong()
    }

    fun insertSet(set: CurrentWorkoutData.Set): Long? = actionResolver {
        val exerciseWithSets = exerciseWithSetsList.find { it.exercise.id == set.exerciseId }
        exerciseWithSets?.setList?.add(set)
        set.id.toLong()
    }

    fun deleteSetFromExercise(set: CurrentWorkoutData.Set): Long? = actionResolver {
        val exerciseWithSets = exerciseWithSetsList.find { it.exercise.id == set.exerciseId }
        exerciseWithSets?.setList?.remove(set)
        set.id.toLong()
    }

    fun deleteLastSet(exerciseId: Int): Long? = actionResolver {
        val exerciseWithSets = exerciseWithSetsList.find { it.exercise.id == exerciseId }
        val lastSet = exerciseWithSets?.setList?.get(exerciseWithSets.setList.size - 1)
        exerciseWithSets?.setList?.remove(lastSet)
        if (exerciseWithSets?.setList?.isEmpty()!!) {
            exerciseWithSetsList.remove(exerciseWithSets)
        }
        return@actionResolver lastSet!!.id.toLong()
    }

    fun clearData() {
        exerciseWithSetsList.clear()
    }

    private fun actionResolver(action: () -> Long): Long? {
        return try {
            action()
        } catch (e: NullPointerException) {
            null
        }
    }
    companion object {
        const val LOADING = 2L
        const val SUCCESS = 1L
        const val EMPTY = 0L
        const val ERROR = -1L
    }
}
