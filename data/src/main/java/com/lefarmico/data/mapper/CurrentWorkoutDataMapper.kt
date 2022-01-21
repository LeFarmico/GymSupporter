package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutDto.Set.toData() = CurrentWorkoutData.Set(
    id = id, exerciseId = exerciseId, setNumber = setNumber, weight = weight, reps = reps
)

@JvmName("CurrentWorkoutDtoSetToData")
fun List<CurrentWorkoutDto.Set>.toData() = this.map { it.toData() }.toMutableList()

fun CurrentWorkoutDto.Exercise.toData() = CurrentWorkoutData.Exercise(id, libraryId, title)

fun List<CurrentWorkoutDto.Exercise>.toData() = this.map { it.toData() }

fun CurrentWorkoutDto.ExerciseWithSets.toData() = CurrentWorkoutData.ExerciseWithSets(
    exercise.toData(), setList.toData()
)
@JvmName("CurrentWorkoutDtoExerciseWithSetsToData")
fun List<CurrentWorkoutDto.ExerciseWithSets>.toData() = this.map { it.toData() }
