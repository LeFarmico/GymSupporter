package com.lefarmico.core.mapper

import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.domain.entity.LibraryDto

fun LibraryDto.Category.toData() = LibraryViewData.Category(
    id = id,
    title = title
)

fun LibraryDto.SubCategory.toData() = LibraryViewData.SubCategory(
    id = id,
    title = title,
    categoryId = categoryId
)

fun LibraryDto.Exercise.toData() = LibraryViewData.Exercise(
    id = id,
    title = title,
    description = description,
    imageRes = imageRes,
    subCategoryId = subCategoryId
)

fun List<LibraryDto.Category>.toDtoCategoryList() = this.map { it.toData() }

fun List<LibraryDto.SubCategory>.toDtoSubCategoryList() = this.map { it.toData() }

fun List<LibraryDto.Exercise>.toDtoExerciseList() = this.map { it.toData() }
