package com.lefarmico.data.mapper

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

@JvmName("LibraryDataCategoryToDto")
fun List<LibraryData.Category>.toDto() = this.map { it.toDto() }

@JvmName("LibraryDataSubCategoryToDto")
fun List<LibraryData.SubCategory>.toDto() = this.map { it.toDto() }

@JvmName("LibraryDataExerciseToDto")
fun List<LibraryData.Exercise>.toDto() = this.map { it.toDto() }
