package com.lefarmico.donetime.data.entities.currentExercise

class ExerciseData private constructor(
    var name: String,
    var tag: String
) : ICurrentExerciseItem {

    class Builder {
        private lateinit var name: String
        private lateinit var tag: String
        private lateinit var addButtonCallback: ((ExerciseData) -> Unit)
        private lateinit var delButtonCallback: ((ExerciseData) -> Unit)

        fun setName(name: String): Builder {
            this.name = name
            return this
        }

        fun setTag(tag: String): Builder {
            this.tag = tag
            return this
        }
        fun setAddButtonCallback(callback: (ExerciseData) -> Unit): Builder {
            addButtonCallback = callback
            return this
        }

        fun setDelButtonCallback(callback: (ExerciseData) -> Unit): Builder {
            delButtonCallback = callback
            return this
        }

        fun build(): ExerciseData {
            return ExerciseData(name, tag).apply {
                this.addButtonEvent = addButtonCallback
                this.delButtonEvent = delButtonCallback
            }
        }
    }

    var exerciseSetList: ExerciseSetList = ExerciseSetList(mutableListOf(ExerciseSet(0f, 0)))
    private var addDelButtons = ExerciseButtons(
        {
            addButtonEvent(this)
        },
        {
            delSet()
            delButtonEvent(this)
        }
    )

    lateinit var addButtonEvent: ((ExerciseData) -> Unit)
    lateinit var delButtonEvent: ((ExerciseData) -> Unit)
    var isActive: Boolean = true

    fun getSetCount(): Int {
        return exerciseSetList.setList.size
    }

    fun addSet(set: ExerciseSet) {
        set.setNumber = exerciseSetList.setList.size + 1
        exerciseSetList.setList.add(set)
    }
    private fun delSet() {
        exerciseSetList.setList.removeAt(exerciseSetList.setList.size - 1)
    }
    
    fun getItems(): MutableList<ICurrentExerciseItem> {
        val itemList = mutableListOf<ICurrentExerciseItem>()
        itemList.add(this)
        if (isActive) {
            itemList.add(addDelButtons)
        }
        return itemList
    }
}
