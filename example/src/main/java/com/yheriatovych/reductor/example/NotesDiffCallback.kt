package com.yheriatovych.reductor.example

import androidx.recyclerview.widget.DiffUtil
import com.yheriatovych.reductor.example.model.Note

internal class NotesDiffCallback(
        private val oldNotes: List<Note>, private val newNotes: List<Note>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldNotes.size
    }

    override fun getNewListSize(): Int {
        return newNotes.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotes[oldItemPosition].id == newNotes[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotes[oldItemPosition] == newNotes[newItemPosition]
    }
}
