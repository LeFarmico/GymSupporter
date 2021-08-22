package com.lefarmico.donetime.utils

import com.lefarmico.donetime.data.entities.currentExercise.ExerciseData
import com.lefarmico.donetime.data.entities.currentExercise.WorkoutData
import com.lefarmico.donetime.data.entities.library.LibraryCategory
import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.data.entities.library.LibrarySubCategory
import com.lefarmico.donetime.data.entities.note.NoteExercise
import com.lefarmico.donetime.data.entities.note.NoteSet
import com.lefarmico.donetime.data.entities.note.NoteWorkout

object Converter {

    fun convertLibraryCategoryToItemCategory(categoryDao: LibraryCategory): LibraryCategory {
        return LibraryCategory(
            title = categoryDao.title,
            id = categoryDao.id
        )
    }

    fun convertLibrarySubCategoryToItemSubCategory(subCategory: LibrarySubCategory): LibrarySubCategory {
        return LibrarySubCategory(
            title = subCategory.title,
            id = subCategory.id,
            categoryId = subCategory.categoryId,

        )
    }

    fun convertLibraryExerciseToItemExercise(exercise: LibraryExercise): LibraryExercise {
        return LibraryExercise(
            title = exercise.title,
            description = exercise.description,
            image = exercise.image,
            id = exercise.id,
            subCategoryId = exercise.subCategoryId
        )
    }

    fun conVertExDataManagerToWorkoutNote(workoutData: WorkoutData): NoteWorkout {
        val noteExerciseList: List<NoteExercise> = workoutData.exercises.map {
            convertExerciseDataToExerciseNote(it)
        }
        return NoteWorkout(
            date = workoutData.date,
            noteExerciseList = noteExerciseList.toMutableList()
        )
    }
    fun convertExerciseDataToExerciseNote(exerciseData: ExerciseData): NoteExercise {
        val noteSetList: List<NoteSet> = exerciseData.exerciseSetList.setList.map {
            NoteSet(
                weight = it.weights,
                reps = it.reps,
                setNumber = it.setNumber
            )
        }
        return NoteExercise(
            exerciseName = exerciseData.name,
            noteSetList = noteSetList
        )
    }
}
