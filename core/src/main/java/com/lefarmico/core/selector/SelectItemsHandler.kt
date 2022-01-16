package com.lefarmico.core.selector

import androidx.recyclerview.widget.RecyclerView

/**
 * class [SelectItemsHandler] uses for handling of the [RecyclerView] items which implements [Callback] interface.
 *
 */
abstract class SelectItemsHandler<T>(private val callback: Callback<T>) {

    /**
     * Triggered on every selected item in the [Callback]
     */
    abstract fun selectedItemAction(item: T)

    fun onEachSelectedItemsAction() {
        callback.getSelectedItems().forEach {
            selectedItemAction(it)
        }
    }

    /**
     * Uses for getting [Set] of items which are selected in [RecyclerView].
     */
    interface Callback<out T> {
        /**
         * returns [Set] of selected items.
         */
        fun getSelectedItems(): Set<T>
    }
}
