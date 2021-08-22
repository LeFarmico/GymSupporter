package com.lefarmico.donetime.data.entities.currentExercise

import com.lefarmico.donetime.data.models.ICurrentExerciseItem
import com.lefarmico.donetime.utils.ItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.donetime.utils.Utilities

class ExerciseDataManager : ItemObservable<ICurrentExerciseItem>, IExerciseDataManager {

    val id: Int = 0
    override val date: String = Utilities.getCurrentDateInFormat()
    override var exercises = mutableListOf<ExerciseData>()

    lateinit var newSet: () -> ExerciseSetEntity
    override val listObservers: MutableList<ItemObserver<ICurrentExerciseItem>> = mutableListOf()

    fun addExercise(name: String, tag: String) {
        val exercise = ExerciseData.Builder()
            .setName(name)
            .setTag(tag)
            .setAddButtonCallback {
                it.addSet(newSet.invoke())
                notifyObservers()
            }
            .setDelButtonCallback {
                deleteEmptyExercise(it)
                notifyObservers()
            }.build()
        exercises.add(exercise)
        notifyObservers()
    }

    private fun deleteExercise(exerciseData: ExerciseData) {
        exercises.remove(exerciseData)
        notifyObservers()
    }

    private fun getItems(): MutableList<ICurrentExerciseItem> {
        val itemList = mutableListOf<ICurrentExerciseItem>()
        exercises.forEach {
            itemList.addAll(it.getItems())
        }
        return itemList
    }

    private fun deleteEmptyExercise(exerciseData: ExerciseData) {
        if (exerciseData.getSetCount() == 0) {
            deleteExercise(exerciseData)
        }
    }

    override fun registerObserver(observer: ItemObserver<ICurrentExerciseItem>) {
        observer.updateData(getItems())
        listObservers.add(observer)
    }

    override fun removeObserver(observer: ItemObserver<ICurrentExerciseItem>) {
        listObservers.remove(observer)
    }

    override fun notifyObservers() {
        listObservers.forEach { 
            it.updateData(getItems())
        }
    }
}
