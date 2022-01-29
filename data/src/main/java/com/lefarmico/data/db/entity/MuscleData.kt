package com.lefarmico.data.db.entity

sealed class MuscleData {

    data class Category(
        val title: String = "",
        val subCategoryList: List<SubCategory> = listOf()
    ) : MuscleData()

    data class SubCategory(
        val title: String = "",
        val exerciseList: List<Exercise> = listOf()
    ) : MuscleData()

    data class Exercise(
        val title: String,
        val description: String = "",
        val imageRes: String = "",
    ) : MuscleData()
}
