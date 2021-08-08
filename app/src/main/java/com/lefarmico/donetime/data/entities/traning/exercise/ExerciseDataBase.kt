package com.lefarmico.donetime.data.entities.traning.exercise

import com.lefarmico.donetime.adapters.viewHolders.factories.WorkoutViewHolderFactory
import com.lefarmico.donetime.utils.ItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

class ExerciseDataBase : ItemType, ItemObservable {

    private val sets: MutableList<ISetEntity> = mutableListOf()
    private lateinit var exerciseName: ExerciseNameEntity
    private var addDelButtons = AddDelButtonsEntity(
        { addButtonEvent?.let { it() } },
        { delButtonEvent?.let { it() } }
    )

    var isActive: Boolean = false
        set(value) {
            field = value
            notifyObservers()
        }
    var addButtonEvent: (() -> Unit)? = null
    var delButtonEvent: (() -> Unit)? = null

    override val listObservers = mutableListOf<ItemObserver>()

    fun addSet(weights: Float, reps: Int) {
        sets.add(
            ExerciseMuscleSetEntity(weights, reps).apply {
                setNumber = sets.size + 1
            }
        )
        notifyObservers()
    }
    fun addSet(ISetEntity: ISetEntity) {
        ISetEntity.setNumber = sets.size + 1
        sets.add(ISetEntity)
        notifyObservers()
    }
    fun delSet() {
        sets.removeAt(sets.size - 1)
        notifyObservers()
    }
    
    private fun getItems(): MutableList<ItemType> {
        val itemList = mutableListOf<ItemType>()
        itemList.add(exerciseName)
        itemList.addAll(sets)
        if (isActive) {
            itemList.add(addDelButtons)
        }
        return itemList
    }

    fun setNameAndTags(name: String, tags: String) {
        exerciseName = ExerciseNameEntity(name, tags)
        notifyObservers()
    }

    fun getSetCount(): Int {
        return sets.size
    }

    override val type: IViewHolderFactory<ItemType> = WorkoutViewHolderFactory.EXERCISE

    override fun registerObserver(observer: ItemObserver) {
        listObservers.add(observer)
        observer.updateData(getItems())
    }

    override fun removeObserver(observer: ItemObserver) {
        listObservers.remove(observer)
    }

    override fun notifyObservers() {
        listObservers.forEach {
            it.updateData(getItems())
        }
    }
}
