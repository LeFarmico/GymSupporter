package com.lefarmico.core.toolbar

sealed class EditActionBarEvents {

    object SelectAll : EditActionBarEvents()

    object DeleteItems : EditActionBarEvents()

    object Launch : EditActionBarEvents()

    object Close : EditActionBarEvents()
}
