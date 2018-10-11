package com.yheriatovych.reductor.example

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

internal class NoteTouchCallback(
        private val onDismissed: (Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(
            viewHolder: RecyclerView.ViewHolder, direction: Int
    ) {
        onDismissed(viewHolder.adapterPosition)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}
