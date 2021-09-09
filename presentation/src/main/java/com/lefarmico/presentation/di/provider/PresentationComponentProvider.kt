package com.lefarmico.presentation.di.provider

import com.lefarmico.presentation.di.PresentationComponent

interface PresentationComponentProvider {
    
    fun getPresentationComponent(): PresentationComponent
}
