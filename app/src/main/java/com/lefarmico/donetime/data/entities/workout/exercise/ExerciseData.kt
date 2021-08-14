package com.lefarmico.donetime.data.entities.workout.exercise

import com.lefarmico.donetime.adapters.viewHolders.factories.WorkoutViewHolderFactory
import com.lefarmico.donetime.utils.ItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

class ExerciseData(override var name: String, override var tag: String) : ItemObservable, IExerciseData {

    private var exerciseName: ExerciseNameEntity = ExerciseNameEntity(name, tag)
    override var sets: MutableList<ISetEntity> = mutableListOf(ExerciseMuscleSetEntity(0f, 0))
    private var addDelButtons = AddDelButtonsEntity(
        {
            addSet(addButtonEvent())
        },
        {
            delSet()
            delButtonEvent(this)
        }
    )
    override lateinit var addButtonEvent: (() -> ISetEntity)
    override lateinit var delButtonEvent: ((ExerciseData) -> Unit)
    override val listObservers = mutableListOf<ItemObserver>()
    override var isActive: Boolean = false
        set(value) {
            field = value
            notifyObservers()
        }

    override fun getSetCount(): Int {
        return sets.size
    }

    fun addSet(setEntity: ISetEntity) {
        setEntity.setNumber = sets.size + 1
        sets.add(setEntity)
        notifyObservers()
    }
    private fun delSet() {
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
