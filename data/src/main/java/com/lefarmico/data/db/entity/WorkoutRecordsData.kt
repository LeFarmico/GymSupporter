package com.lefarmico.data.db.entity

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.lefarmico.data.utils.TypeConverter

sealed class WorkoutRecordsData {

    @Entity(
        tableName = "workout_records",
        indices = [
            Index(
                value = ["id"],
                unique = true
            )
        ]
    )
    @TypeConverters(TypeConverter.ExerciseList::class)
    data class Workout(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "date") val date: String = "",
        @ColumnInfo(name = "exercises_record") val exerciseList: List<Exercise>
    ) : WorkoutRecordsData()

    @TypeConverters(TypeConverter.SetsList::class)
    data class Exercise(
        val id: Int,
        val exerciseName: String,
        val noteSetList: List<Set>
    ) : WorkoutRecordsData()

    data class Set(
        val id: Int,
        val exerciseId: Int,
        val setNumber: Int,
        val weight: Float,
        val reps: Int,
        val measureType: MeasureType
    ) : WorkoutRecordsData()

    enum class MeasureType(val typeNumber: Int) {
        KILO(1), LB(2)
    }
}
