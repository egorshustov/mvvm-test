package com.egorshustov.mvvmtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


class MainActivity : AppCompatActivity() {

    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Android system will destroy following viewModel when 'this' activity is finished:
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        // Lets get all notes:
        appViewModel.getAllNotes().observe(this, Observer<List<Note>> {
            // Update RecyclerView
            Toast.makeText(this@MainActivity, "onChanged", Toast.LENGTH_SHORT).show()
        })
    }
}