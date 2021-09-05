package com.lefarmico.data.utils

import com.lefarmico.data.db.entity.LibraryData
import com.lefarmico.domain.entity.LibraryDto

fun LibraryData.Category.toDto() = LibraryDto.Category(
    id = id,
    title = title
)

fun LibraryData.SubCategory.toDto() = LibraryDto.SubCategory(
    id = id,
    title = title,
    categoryId = categoryId
)

fun LibraryData.Exercise.toDto() = LibraryDto.Exercise(
    id = id,
    title = title,
    description = description,
    imageRes = imageRes,
    subCategoryId = subCategoryId
)

fun List<LibraryData.Category>.toDtoCategoryList() = this.map { it.toDto() }

fun List<LibraryData.SubCategory>.toDtoSubCategoryList() = this.map { it.toDto() }

fun List<LibraryData.Exercise>.toDtoExerciseList() = this.map { it.toDto() }
