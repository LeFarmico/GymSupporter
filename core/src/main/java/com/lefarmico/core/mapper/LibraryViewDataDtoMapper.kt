package com.lefarmico.core.mapper

import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.domain.entity.LibraryDto

fun LibraryViewData.Category.toDto() = LibraryDto.Category(
    id = id,
    title = title
)

fun LibraryViewData.SubCategory.toDto() = LibraryDto.SubCategory(
    id = id,
    title = title,
    categoryId = categoryId
)

fun LibraryViewData.Exercise.toDto() = LibraryDto.Exercise(
    id = id,
    title = title,
    description = description,
    imageRes = imageRes,
    subCategoryId = subCategoryId
)

fun List<LibraryViewData.Category>.toViewDataCategory() = this.map { it.toDto() }

fun List<LibraryViewData.SubCategory>.toViewDataSubCategory() = this.map { it.toDto() }

fun List<LibraryViewData.Exercise>.toViewDataExercise() = this.map { it.toDto() }
