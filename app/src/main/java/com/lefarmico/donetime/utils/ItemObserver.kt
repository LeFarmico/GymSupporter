package com.lefarmico.donetime.utils

import com.lefarmico.lerecycle.ItemType

interface ItemObserver {
    fun updateData(items: MutableList<ItemType>)
}
