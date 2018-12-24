package com.egorshustov.mvvmtest.addeditnote

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
import android.nfc.NfcAdapter.EXTRA_ID
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import android.R.attr.data






class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var numberPickerPriority: NumberPicker
    lateinit var addEditNoteViewModel: AddEditNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        addEditNoteViewModel = ViewModelProviders.of(this).get(AddEditNoteViewModel::class.java)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)
        numberPickerPriority = findViewById(R.id.number_picker_priority)

        numberPickerPriority.minValue = 1
        numberPickerPriority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        val intent = intent

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            numberPickerPriority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add Note"
        }
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
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            note.id = id
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
            addEditNoteViewModel.update(note)
        } else {
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            addEditNoteViewModel.insert(note)
        }
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

    companion object {
        const val EXTRA_ID = "com.egorshustov.mvvmtest.EXTRA_ID"
        const val EXTRA_TITLE = "com.egorshustov.mvvmtest.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.egorshustov.mvvmtest.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.egorshustov.mvvmtest.EXTRA_PRIORITY"
    }
}



