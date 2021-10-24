package com.lefarmico.domain.entity

sealed class CurrentWorkoutDto {

    data class Exercise private constructor(
        val id: Int = 0,
        val libraryId: Int,
        val title: String
    ) : CurrentWorkoutDto() {

        class Builder {
            lateinit var title: String
                private set
            var id: Int? = null
                private set
            var libraryId: Int? = null
                private set

            fun setId(id: Int) = apply { this.id = id }
            fun setTitle(title: String) = apply { this.title = title }
            fun setLibraryId(libraryId: Int) = apply { this.libraryId = libraryId }
            fun build(): Exercise {
                return if (id == null) {
                    Exercise(title = title, libraryId = libraryId!!)
                } else {
                    Exercise(id!!, libraryId = libraryId!!, title)
                }
            }
        }
    }

    data class Set(
        val id: Int,
        val exerciseId: Int,
        val setNumber: Int,
        val weight: Float,
        val reps: Int
    ) : CurrentWorkoutDto()

    data class ExerciseWithSets(
        val exercise: Exercise,
        val setList: List<Set> = mutableListOf()
    ) : CurrentWorkoutDto()
}
