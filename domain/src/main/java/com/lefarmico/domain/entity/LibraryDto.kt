package com.lefarmico.domain.entity

sealed class LibraryDto {
    
    data class Category(
        val id: Int = 0,
        val title: String = ""
    ) : LibraryDto()
    
    data class SubCategory(
        val id: Int = 0,
        val title: String = "",
        val categoryId: Int
    ) : LibraryDto()
    
    data class Exercise(
        val id: Int = 0,
        val title: String,
        val description: String = "",
        val imageRes: String = "",
        val subCategoryId: Int
    ) : LibraryDto()
}
