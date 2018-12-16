package com.egorshustov.mvvmtest

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.NumberPicker
import android.widget.EditText


class AddNoteActivity : AppCompatActivity() {

    private var editTextTitle: EditText? = null
    private var editTextDescription: EditText? = null
    private var numberPickerPriority: NumberPicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)
        numberPickerPriority = findViewById(R.id.number_picker_priority)

        numberPickerPriority!!.minValue = 1
        numberPickerPriority!!.maxValue = 10

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add Note"
    }

    private fun saveNote() {
        val title = editTextTitle!!.text.toString()
        val description = editTextDescription!!.text.toString()
        val priority = numberPickerPriority!!.value

        if (title.trim { it <= ' ' }.isEmpty() || description.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)
        data.putExtra(EXTRA_PRIORITY, priority)

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val EXTRA_TITLE = "com.egorshustov.mvvmtest.EXTRA_TITLE"
        val EXTRA_DESCRIPTION = "com.egorshustov.mvvmtest.EXTRA_DESCRIPTION"
        val EXTRA_PRIORITY = "com.egorshustov.mvvmtest.EXTRA_PRIORITY"
    }
}



