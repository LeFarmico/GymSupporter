package com.lefarmico.donetime.data.entities.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lefarmico.donetime.utils.ExercisesTypeConverter

@Entity(
    tableName = "workout_history",
    indices = [
        Index(
            value = ["id"],
            unique = true
        )
    ]
)
@TypeConverters(ExercisesTypeConverter::class)
class NoteWorkout(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "workout_note") val noteExerciseList: MutableList<NoteExercise> = mutableListOf()
)
