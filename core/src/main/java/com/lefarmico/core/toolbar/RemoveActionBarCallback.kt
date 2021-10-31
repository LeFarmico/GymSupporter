package com.lefarmico.core.toolbar

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.lefarmico.core.R

abstract class RemoveActionBarCallback : ActionMode.Callback2() {

    abstract fun selectAllButtonHandler()
    abstract fun removeButtonHandler()
    abstract fun onDestroyHandler()

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode!!.menuInflater.inflate(R.menu.delete_action_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.select_all -> {
                selectAllButtonHandler()
                return true
            }
            R.id.remove_items -> {
                removeButtonHandler()
                return true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        onDestroyHandler()
        mode!!.finish()
    }
}
