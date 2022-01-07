package com.lefarmico.core.mapper

import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.domain.entity.LibraryDto

fun LibraryDto.Category.toViewData() = LibraryViewData.Category(
    id = id,
    title = title
)

fun LibraryDto.SubCategory.toViewData() = LibraryViewData.SubCategory(
    id = id,
    title = title,
    categoryId = categoryId
)

fun LibraryDto.Exercise.toViewData() = LibraryViewData.Exercise(
    id = id,
    title = title,
    description = description,
    imageRes = imageRes,
    subCategoryId = subCategoryId
)

fun List<LibraryDto.Category>.toViewDataCategory() = this.map { it.toViewData() }

fun List<LibraryDto.SubCategory>.toViewDataSubCategory() = this.map { it.toViewData() }

@JvmName("toLibraryViewDataExercise")
fun List<LibraryDto.Exercise>.toViewData() = this.map { it.toViewData() }
