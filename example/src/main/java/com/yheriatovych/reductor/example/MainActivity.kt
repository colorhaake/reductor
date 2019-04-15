package com.yheriatovych.reductor.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.yheriatovych.reductor.Actions
import com.yheriatovych.reductor.Cancelable
import com.yheriatovych.reductor.Cursors
import com.yheriatovych.reductor.Store
import com.yheriatovych.reductor.example.model.AppState
import com.yheriatovych.reductor.example.model.NotesFilter
import com.yheriatovych.reductor.example.reductor.filter.FilterActions
import com.yheriatovych.reductor.example.reductor.notelist.NotesActions
import com.yheriatovych.reductor.example.reductor.utils.UndoableReducer
import kotlinx.android.synthetic.main.activity_main.*

import java.util.concurrent.atomic.AtomicInteger

class MainActivity : AppCompatActivity() {

    private lateinit var store: Store<AppState>
    private lateinit var mCancelable: Cancelable
    private val idGenerator = AtomicInteger()
    private val notesActions: NotesActions by lazy { Actions.from(NotesActions::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = (applicationContext as ReductorApp).store
        setContentView(R.layout.activity_main)

        setupSpinner(spinner, store)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val adapter = TodoAdapter(store.state.notes()) {
            note -> store.dispatch(notesActions.toggle(note.id))
        }

        ItemTouchHelper(
                NoteTouchCallback({ position ->
                    val id = store.state.notes()[position].id
                    store.dispatch(notesActions.remove(id))
                })
        )
                .attachToRecyclerView(recyclerview)


        adapter.setHasStableIds(true)
        recyclerview.adapter = adapter

        mCancelable = Cursors.forEach(store) { state ->
            adapter.setNotes(state.filteredNotes)
            spinner.setSelection(state.filter().ordinal)
        }

        add.setOnClickListener {
            val note = note_edit_text.text.toString()
            val id = idGenerator.getAndIncrement()
            store.dispatch(notesActions.add(id, note))
            note_edit_text.text = null
        }
    }

    private fun setupSpinner(spinner: Spinner, store: Store<AppState>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, NotesFilter.values())
        spinner.adapter = adapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                val filter = NotesFilter.values()[position]
                store.dispatch(Actions.from(FilterActions::class.java).setFilter(filter))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(R.string.undo)
                .setIcon(R.drawable.ic_undo_24dp)
                .setOnMenuItemClickListener {
                    store.dispatch(UndoableReducer.pop())
                    true
                }
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return true
    }

    override fun onDestroy() {
        mCancelable.cancel()
        super.onDestroy()
    }

}
