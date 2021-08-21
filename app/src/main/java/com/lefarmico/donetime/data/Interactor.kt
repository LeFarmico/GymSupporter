package com.lefarmico.donetime.data

import com.lefarmico.donetime.data.db.entities.WorkoutNote
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryExercise
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory
import com.lefarmico.donetime.data.entities.workout.WorkoutData
import com.lefarmico.donetime.utils.Converter
import com.lefarmico.donetime.utils.JsonConverter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class Interactor(
    private val exRepo: ExerciseLibraryRepository,
    private val workoutRepo: WorkoutNotesRepository
) {
    fun getCategoriesFromDB(): Observable<List<ItemLibraryCategory>> {
        return exRepo.getExerciseCategories()
            .subscribeOn(Schedulers.io())
            .map { list ->
                list.map { category ->
                    Converter.convertLibraryCategoryToItemCategory(category)
                }
            }
    }

    fun getSubCategoriesFromDB(categoryId: Int): Observable<List<ItemLibrarySubCategory>> {
        return exRepo.getSubCategories(categoryId)
            .observeOn(Schedulers.io())
            .map { list ->
                list.map { subCategory ->
                    Converter.convertLibrarySubCategoryToItemSubCategory(subCategory)
                }
            }
    }

    fun getExercisesFromDB(subCategoryId: Int): Observable<List<ItemLibraryExercise>> {
        return exRepo.getExercises(subCategoryId)
            .observeOn(Schedulers.io())
            .map { list ->
                list.map { ex ->
                    Converter.convertLibraryExerciseToItemExercise(ex)
                }
            }
    }

    fun addWorkoutNoteToDB(workoutData: WorkoutData) {
        Single.create<WorkoutData> {
            it.onSuccess(workoutData)
        }
            .subscribeOn(Schedulers.io())
            .subscribe { data ->
                val workoutNote = WorkoutNote(
                    date = data.date,
                    workoutEntity = JsonConverter.fromWorkoutDataToJson(data)
                )
                workoutRepo.addWorkoutNote(workoutNote)
            }
    }

    fun addWorkoutData(workoutData: WorkoutData) {
        Single.create<WorkoutData> {
            it.onSuccess(workoutData)
        }
            .subscribeOn(Schedulers.io())
            .subscribe { data ->
                workoutRepo.addWorkoutData(data)
            }
    }
}
