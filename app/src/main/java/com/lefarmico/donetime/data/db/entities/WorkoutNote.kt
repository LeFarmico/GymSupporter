package com.lefarmico.donetime.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_history",
    indices = [
        Index(
            value = ["id"],
            unique = true
        )
    ]
)
data class WorkoutNote(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "workout_note") val workoutEntity: String = ""
)
