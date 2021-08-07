package com.lefarmico.donetime.viewModels

import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.db.ExerciseLibraryRepository
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class ExerciseListViewModel : BaseViewModel() {

    @Inject lateinit var repo: ExerciseLibraryRepository

    init {
        App.appComponent.inject(this)
//        GlobalScope.launch {
//            addNew()
//        }
    }

    fun addNew() {
        repo.addCategory("Силовые")
        val cat = repo.getExerciseCategories()[0]
        repo.addSubCategory("Грудь", cat.id)
        val subCat = repo.getSubCategories(cat.id)[0]
        repo.addExercise("Жим штанги лежа в наклоне", "Когда жмешь лежа в наклоне", null, subCat.id)
    }
}
