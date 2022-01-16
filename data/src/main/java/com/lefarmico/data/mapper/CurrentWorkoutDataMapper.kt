package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutDto.Set.toData() = CurrentWorkoutData.Set(
    id = id, exerciseId = exerciseId, setNumber = setNumber, weight = weight, reps = reps
)

@JvmName("CurrentWorkoutDtoSetToData")
fun List<CurrentWorkoutDto.Set>.toData() = this.map { it.toData() }
