package com.lefarmico.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

sealed class WorkoutRecordsData {

    @Entity(
        tableName = "workout_records",
        indices = [
            Index(
                value = ["workout_id"],
                unique = true
            )
        ]
    )
    data class Workout(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "workout_id") val id: Int,
        @ColumnInfo(name = "date") val date: LocalDate,
        @ColumnInfo(name = "title") val title: String = ""
    ) : WorkoutRecordsData()

    @Entity(
        tableName = "exercise_records",
        indices = [
            Index(
                value = ["exercise_id"],
                unique = true
            )
        ],
        foreignKeys = [
            ForeignKey(
                entity = Workout::class,
                parentColumns = ["workout_id"],
                childColumns = ["workout_id"],
                onDelete = CASCADE,
                onUpdate = CASCADE
            )
        ]
    )
    data class Exercise(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "exercise_id") val id: Int,
        @ColumnInfo(name = "workout_id") val workoutId: Int,
        @ColumnInfo(name = "exercise_name") val exerciseName: String,
    ) : WorkoutRecordsData()

    @Entity(
        tableName = "set_records",
        indices = [
            Index(
                value = ["set_id"],
                unique = true
            )
        ],
        foreignKeys = [
            ForeignKey(
                entity = Exercise::class,
                parentColumns = ["exercise_id"],
                childColumns = ["exercise_id"],
                onDelete = CASCADE,
                onUpdate = CASCADE
            )
        ]
    )
    data class Set(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "set_id") val id: Int,
        @ColumnInfo(name = "exercise_id") val exerciseId: Int,
        @ColumnInfo(name = "set_number") val setNumber: Int,
        @ColumnInfo(name = "weight") val weight: Float,
        @ColumnInfo(name = "reps") val reps: Int,
        @ColumnInfo(name = "measure_type") val measureType: MeasureType
    ) : WorkoutRecordsData()

    enum class MeasureType(val typeNumber: Int) {
        KILO(1), LB(2)
    }

    data class ExerciseWithSets(
        @Embedded val exercise: Exercise,
        @Relation(
            parentColumn = "exercise_id",
            entityColumn = "exercise_id"
        )
        val setList: List<Set>
    ) : WorkoutRecordsData()

    data class WorkoutWithExercisesAndSets(
        @Embedded val workout: Workout,
        @Relation(
            entity = Exercise::class,
            parentColumn = "workout_id",
            entityColumn = "workout_id"
        )
        val exerciseWithSetsList: List<ExerciseWithSets>
    ) : WorkoutRecordsData()
}
