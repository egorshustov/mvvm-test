package com.egorshustov.mvvmtest.addnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.egorshustov.mvvmtest.R
import com.egorshustov.mvvmtest.data.Note


class AddNoteActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var numberPickerPriority: NumberPicker
    private lateinit var addNoteViewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        addNoteViewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)
        numberPickerPriority = findViewById(R.id.number_picker_priority)

        numberPickerPriority.minValue = 1
        numberPickerPriority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add Note"
    }

    private fun saveNote() {
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val priority = numberPickerPriority.value

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(title, description, priority)
        addNoteViewModel.insert(note)
        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.activity_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



