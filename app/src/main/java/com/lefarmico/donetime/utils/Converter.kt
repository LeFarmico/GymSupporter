package com.lefarmico.donetime.utils

import com.lefarmico.donetime.data.db.entities.LibraryCategory
import com.lefarmico.donetime.data.db.entities.LibraryExercise
import com.lefarmico.donetime.data.db.entities.LibrarySubCategory
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseData
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseDataManager
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryExercise
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory
import com.lefarmico.donetime.data.entities.note.ExerciseNote
import com.lefarmico.donetime.data.entities.note.SetNote
import com.lefarmico.donetime.data.entities.note.WorkoutNote

object Converter {

    fun convertLibraryCategoryToItemCategory(categoryDao: LibraryCategory): ItemLibraryCategory {
        return ItemLibraryCategory(
            title = categoryDao.categoryTitle,
            id = categoryDao.id
        )
    }

    fun convertLibrarySubCategoryToItemSubCategory(subCategory: LibrarySubCategory): ItemLibrarySubCategory {
        return ItemLibrarySubCategory(
            title = subCategory.subCategory,
            id = subCategory.id,
            categoryId = subCategory.categoryId,

        )
    }

    fun convertLibraryExerciseToItemExercise(exercise: LibraryExercise): ItemLibraryExercise {
        return ItemLibraryExercise(
            title = exercise.title,
            description = exercise.description,
            image = exercise.image,
            id = exercise.id,
            subCategoryId = exercise.subCategoryId
        )
    }

    fun conVertExDataManagerToWorkoutNote(exerciseDataManager: ExerciseDataManager): WorkoutNote {
        val exerciseNoteList: List<ExerciseNote> = exerciseDataManager.exercises.map {
            convertExerciseDataToExerciseNote(it)
        }
        return WorkoutNote(
            date = exerciseDataManager.date,
            exerciseNoteList = exerciseNoteList.toMutableList()
        )
    }
    fun convertExerciseDataToExerciseNote(exerciseData: ExerciseData): ExerciseNote {
        val setNoteList: List<SetNote> = exerciseData.exerciseSetList.setList.map {
            SetNote(
                weight = it.weights,
                reps = it.reps,
                setNumber = it.setNumber
            )
        }
        return ExerciseNote(
            exerciseName = exerciseData.name,
            setNoteList = setNoteList
        )
    }
}
