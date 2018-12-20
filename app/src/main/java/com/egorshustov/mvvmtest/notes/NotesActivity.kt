package com.egorshustov.mvvmtest.notes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egorshustov.mvvmtest.R
import com.egorshustov.mvvmtest.addnote.AddNoteActivity
import com.egorshustov.mvvmtest.data.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotesActivity : AppCompatActivity() {

    private lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val buttonAddNote = findViewById<FloatingActionButton>(R.id.button_add_note)
        buttonAddNote.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@NotesActivity, AddNoteActivity::class.java)
                startActivity(intent)
            }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_notes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val noteAdapter = NotesAdapter()
        recyclerView.adapter = noteAdapter

        // Android system will destroy following viewModel when 'this' activity is finished:
        // Let's get all notes:
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        notesViewModel.getAllNotes().observe(this,
           Observer<List<Note>> {
                   notes -> noteAdapter.replaceNotes(notes)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.activity_notes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_delete_notes -> {
                notesViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}