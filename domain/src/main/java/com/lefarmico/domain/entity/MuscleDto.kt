package com.lefarmico.domain.entity

sealed class MuscleDto {

    data class Category(
        val title: String = "",
        val subCategoryList: List<SubCategory> = listOf()
    ) : MuscleDto()

    data class SubCategory(
        val title: String = "",
        val exerciseList: List<Exercise> = listOf()
    ) : MuscleDto()

    data class Exercise(
        val title: String,
        val description: String = "",
        val imageRes: String = "",
    ) : MuscleDto()
}
