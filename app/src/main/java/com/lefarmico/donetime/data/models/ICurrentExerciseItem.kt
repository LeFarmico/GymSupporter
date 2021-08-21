package com.lefarmico.donetime.data.models

interface ICurrentExerciseItem

interface ICurrentExerciseSetItem : ICurrentExerciseItem {
    var setNumber: Int
    val weights: Float
    val reps: Int
}
