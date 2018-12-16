package com.egorshustov.mvvmtest

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


class MainActivity : AppCompatActivity() {

    val ADD_NOTE_REQUEST = 1
    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddNote = findViewById<FloatingActionButton>(R.id.button_add_note)
        buttonAddNote.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
                startActivityForResult(intent, ADD_NOTE_REQUEST)
            }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_notes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // Android system will destroy following viewModel when 'this' activity is finished:
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        // Let's get all notes:
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        appViewModel.getAllNotes().observe(this,
           Observer<List<Note>> { notes -> recyclerView.adapter = NoteAdapter(notes)})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title = data!!.getStringExtra(AddNoteActivity.EXTRA_TITLE)
            val description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(title, description, priority)
            appViewModel.insert(note)

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }
}