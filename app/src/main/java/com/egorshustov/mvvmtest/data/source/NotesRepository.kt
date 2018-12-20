package com.egorshustov.mvvmtest.data.source

import androidx.lifecycle.LiveData
import android.app.Application
import com.egorshustov.mvvmtest.data.source.local.AppDatabase
import com.egorshustov.mvvmtest.data.source.local.NotesDao
import com.egorshustov.mvvmtest.data.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NotesRepository(application: Application) {
    private val notesDao: NotesDao
    private val allNotes: LiveData<List<Note>>

    init {
        val database = AppDatabase.getInstance(application)
        notesDao = database.noteDao()
        allNotes = notesDao.allNotes
    }

    // These methods are the API that NotesRepository exposes
    // to the outside:
    fun insert(note: Note) {
        GlobalScope.launch {
            notesDao.insert(note)
        }
    }

    fun update(note: Note) {
        GlobalScope.launch {
            notesDao.update(note)
        }
    }

    fun delete(note: Note) {
        GlobalScope.launch {
            notesDao.delete(note)
        }
    }

    fun deleteAllNotes() {
        GlobalScope.launch {
            notesDao.deleteAllNotes()
        }
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}