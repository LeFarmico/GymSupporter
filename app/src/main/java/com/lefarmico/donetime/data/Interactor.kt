package com.lefarmico.donetime.data

import com.lefarmico.donetime.data.entities.currentExercise.WorkoutData
import com.lefarmico.donetime.data.entities.library.LibraryCategory
import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.data.entities.library.LibrarySubCategory
import com.lefarmico.donetime.data.entities.note.NoteWorkout
import com.lefarmico.donetime.utils.Converter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class Interactor(
    private val exRepo: ExerciseLibraryRepository,
    private val workoutRepo: WorkoutNotesRepository
) {
    fun getCategoriesFromDB(): Observable<List<LibraryCategory>> {
        return exRepo.getExerciseCategories()
            .subscribeOn(Schedulers.io())
            .map { list ->
                list.map { category ->
                    Converter.convertLibraryCategoryToItemCategory(category)
                }
            }
    }

    fun getSubCategoriesFromDB(categoryId: Int): Observable<List<LibrarySubCategory>> {
        return exRepo.getSubCategories(categoryId)
            .observeOn(Schedulers.io())
            .map { list ->
                list.map { subCategory ->
                    Converter.convertLibrarySubCategoryToItemSubCategory(subCategory)
                }
            }
    }

    fun getExercisesFromDB(subCategoryId: Int): Observable<List<LibraryExercise>> {
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
                val workoutNote = Converter.conVertExDataManagerToWorkoutNote(data)
                workoutRepo.addNoteWorkout(workoutNote)
            }
    }

    fun addNewCategory(categoryTitle: String) {
        Single.create<String> {
            it.onSuccess(categoryTitle)
        }
            .subscribeOn(Schedulers.io())
            .subscribe { data ->
                exRepo.addCategory(data)
            }
    }

    fun addNewSubCategory(subCategoryTitle: String, categoryId: Int) {
        Single.create<Pair<String, Int>> {
            it.onSuccess(subCategoryTitle to categoryId)
        }
            .subscribeOn(Schedulers.io())
            .subscribe { data ->
                exRepo.addSubCategory(data.first, data.second)
            }
    }

    fun addNewExercise(title: String, description: String, imageRes: String?, subcategoryId: Int) {
        val exercise = LibraryExercise(
            title = title,
            description = description,
            subCategoryId = subcategoryId
        )
        Single.create<LibraryExercise> {
            it.onSuccess(exercise)
        }
            .subscribeOn(Schedulers.io())
            .subscribe { data ->
                exRepo.addExercise(data)
            }
    }

    fun getNoteWorkoutsFromDB(): Observable<List<NoteWorkout>> {
        return workoutRepo.getNoteWorkouts()
    }

    fun getExerciseById(id: Int): Single<LibraryExercise> {
        return Single.create<LibraryExercise> {
            it.onSuccess(exRepo.getExercise(id))
        }
            .subscribeOn(Schedulers.io())
    }
}
