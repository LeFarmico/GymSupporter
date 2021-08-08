package com.lefarmico.donetime.data

import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.utils.Converter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class Interactor(
    private val repo: ExerciseLibraryRepository

) {
    fun getCategoriesFromDB(): Observable<List<ItemLibraryCategory>> {
        return repo.getExerciseCategories()
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .map { list ->
                list.map { category ->
                    Converter.convertLibraryCategoryToItemCategory(category)
                }
            }
    }
}
