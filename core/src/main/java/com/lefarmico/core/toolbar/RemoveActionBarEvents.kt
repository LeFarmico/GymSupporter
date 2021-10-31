package com.lefarmico.core.toolbar

sealed class RemoveActionBarEvents {

    object SelectAll : RemoveActionBarEvents()

    object DeleteItems : RemoveActionBarEvents()

    object Launch : RemoveActionBarEvents()

    object Close : RemoveActionBarEvents()
}
