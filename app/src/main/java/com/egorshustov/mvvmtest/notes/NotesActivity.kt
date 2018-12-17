package com.egorshustov.mvvmtest.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Toast
import android.app.Activity
import androidx.recyclerview.widget.ItemTouchHelper
import com.egorshustov.mvvmtest.addnote.AddNoteActivity
import com.egorshustov.mvvmtest.data.Note
import com.egorshustov.mvvmtest.R

class NotesActivity : AppCompatActivity() {

    val ADD_NOTE_REQUEST = 1
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddNote = findViewById<FloatingActionButton>(R.id.button_add_note)
        buttonAddNote.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@NotesActivity, AddNoteActivity::class.java)
                startActivityForResult(intent, ADD_NOTE_REQUEST)
            }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_notes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val notesDefault: List<Note>? = null
        val noteAdapter = NotesAdapter(notesDefault)
        recyclerView.adapter = noteAdapter

        // Android system will destroy following viewModel when 'this' activity is finished:
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        // Let's get all notes:
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        notesViewModel.getAllNotes().observe(this,
           Observer<List<Note>?> {
                   notes -> noteAdapter.notes = notes
               noteAdapter.notifyDataSetChanged()
           })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                notesViewModel.delete(noteAdapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@NotesActivity, "Note deleted", Toast.LENGTH_SHORT).show()

            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title = data!!.getStringExtra(AddNoteActivity.EXTRA_TITLE)
            val description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(title, description, priority)
            notesViewModel.insert(note)

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }
}