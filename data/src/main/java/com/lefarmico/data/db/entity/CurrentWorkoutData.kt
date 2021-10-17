package com.lefarmico.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

sealed class CurrentWorkoutData {

    @Entity(
        tableName = "current_exercise"
    )
    data class Exercise(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "exercise_id") val id: Int,
        @ColumnInfo(name = "title") val exerciseName: String
    ) : CurrentWorkoutData()

    @Entity(
        tableName = "current_set"
    )
    data class Set(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "set_id") val id: Int,
        @ColumnInfo(name = "exercise_id") val exerciseId: Int,
        @ColumnInfo(name = "set_number") val setNumber: Int,
        @ColumnInfo(name = "weight") val weight: Float,
        @ColumnInfo(name = "reps") val reps: Int,
        @ColumnInfo(name = "measure_type") val measureType: MeasureType
    ) : CurrentWorkoutData()

    enum class MeasureType(val typeNumber: Int) {
        KILO(1), LB(2)
    }

    @Entity(primaryKeys = ["exercise_id", "set_id"])
    data class ExerciseSetCrossRef(
        @ColumnInfo(name = "exercise_id") val exerciseId: Int,
        @ColumnInfo(name = "set_id") val setId: Int,
    ) : CurrentWorkoutData()

    data class ExerciseWithSets(
        @Embedded val exercise: Exercise,
        @Relation(
            parentColumn = "exercise_id",
            entityColumn = "set_id",
            associateBy = Junction(ExerciseSetCrossRef::class)
        )
        val setList: List<Set>
    ) : CurrentWorkoutData()
}
