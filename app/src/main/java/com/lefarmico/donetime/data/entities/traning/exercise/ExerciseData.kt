package com.lefarmico.donetime.data.entities.traning.exercise

import com.lefarmico.donetime.adapters.viewHolders.factories.WorkoutViewHolderFactory
import com.lefarmico.donetime.utils.ItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

class ExerciseData : ItemType, ItemObservable {

    constructor(name: String, tag: String) {
        this.exerciseName = ExerciseNameEntity(name, tag)
        this.sets.add(ExerciseMuscleSetEntity(0f, 0))
    }

    constructor(name: String, tag: String, setEntity: ISetEntity) {
        this.exerciseName = ExerciseNameEntity(name, tag)
        this.sets.add(setEntity)
    }

    constructor(name: String, tag: String, listSetEntity: List<ISetEntity>) {
        this.exerciseName = ExerciseNameEntity(name, tag)
        this.sets.addAll(listSetEntity)
    }
    private var exerciseName: ExerciseNameEntity
    private val sets: MutableList<ISetEntity> = mutableListOf()
    private var addDelButtons = AddDelButtonsEntity(
        {
            addSet(addButtonEvent())
        },
        {
            delSet()
            delButtonEvent(this)
        }
    )
    lateinit var addButtonEvent: (() -> ISetEntity)
    lateinit var delButtonEvent: ((ExerciseData) -> Unit)

    var isActive: Boolean = false
        set(value) {
            field = value
            notifyObservers()
        }

    override val listObservers = mutableListOf<ItemObserver>()
    
    fun getSetCount(): Int {
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
