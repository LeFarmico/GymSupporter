package com.lefarmico.core.entity

sealed class WorkoutRecordsViewData {

    data class Workout(
        val id: Int = 0,
        val date: String,
        val title: String = ""
    ) : WorkoutRecordsViewData()

    data class Exercise(
        val id: Int,
        val exerciseName: String,
        val workoutId: Int
    ) : WorkoutRecordsViewData(), ViewDataItemType {
        override fun getItemType(): Int = EXERCISE
    }

    data class Set(
        val id: Int,
        val exerciseId: Int,
        val setNumber: Int,
        val weight: Float,
        val reps: Int,
        val measureType: MeasureType
    ) : WorkoutRecordsViewData(), ViewDataItemType {
        override fun getItemType(): Int = SET
    }

    enum class MeasureType(val typeNumber: Int) {
        KILO(1), LB(2)
    }

    data class ExerciseWithSets(
        val exercise: Exercise,
        val setList: List<Set>
    ) : WorkoutRecordsViewData()

    data class WorkoutWithExercisesAndSets(
        val workout: Workout,
        val exerciseWithSetsList: List<ExerciseWithSets>
    ) : WorkoutRecordsViewData()

    interface ViewDataItemType {
        fun getItemType(): Int
    }
    companion object {
        const val SET = 1
        const val EXERCISE = 0
    }
}
