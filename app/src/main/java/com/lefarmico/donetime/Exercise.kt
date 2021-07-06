package com.lefarmico.donetime

data class Exercise(
    val name: String,
    val description: String,
) : ItemType {
    override val type: ItemViewType = ItemViewType.EXERCISE
}

data class ExerciseDate(
    val date: String
) : ItemType {
    override val type: ItemViewType = ItemViewType.DATE
}

interface ItemType {
    val type: ItemViewType
}

enum class ItemViewType(val typeNumber: Int) {
    DATE(1), EXERCISE(2)
}
