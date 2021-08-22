package com.lefarmico.donetime.data.entities.currentExercise

import com.lefarmico.donetime.utils.ItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.donetime.utils.Utilities

class WorkoutData : ItemObservable<ICurrentExerciseItem> {

    val id: Int = 0
    val date: String = Utilities.getCurrentDateInFormat()
    var exercises = mutableListOf<ExerciseData>()

    lateinit var newSet: () -> ExerciseSet
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
