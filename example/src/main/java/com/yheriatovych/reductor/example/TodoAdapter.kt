package com.yheriatovych.reductor.example

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.yheriatovych.reductor.example.model.Note

class TodoAdapter(
        private var notes: List<Note>,
        private val onClickListener: (Note) -> Unit
) : RecyclerView.Adapter<TodoAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.content.text = note.note
        holder.content.isChecked = note.checked
        holder.itemView.setOnClickListener { onClickListener(note) }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun getItemId(position: Int): Long {
        return notes[position].id.toLong()
    }

    fun setNotes(notes: List<Note>) {
        val oldNotes = this.notes
        this.notes = notes
        DiffUtil.calculateDiff(NotesDiffCallback(oldNotes, notes), false).dispatchUpdatesTo(this)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var content: CheckBox = itemView as CheckBox

    }

}
