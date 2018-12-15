package com.egorshustov.mvvmtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_notes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // Android system will destroy following viewModel when 'this' activity is finished:
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        // Lets get all notes:
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        appViewModel.getAllNotes().observe(this,
           Observer<List<Note>> { notes -> recyclerView.adapter = NoteAdapter(notes)})
    }
}