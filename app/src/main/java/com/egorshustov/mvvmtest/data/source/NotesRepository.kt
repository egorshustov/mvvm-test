package com.egorshustov.mvvmtest.data.source

import android.app.Application
import com.egorshustov.mvvmtest.data.Note
import com.egorshustov.mvvmtest.data.source.local.AppDatabase
import com.egorshustov.mvvmtest.data.source.local.NotesDao
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


class NotesRepository (application: Application)
{
    private val noteDao: NotesDao
    init {
        val database = AppDatabase.getInstance(application)
        noteDao = database.noteDao()
    }
    // These methods are the API that NotesRepository exposes
    // to the outside:
    suspend fun insert(note: Note) {
        withContext(IO) {
            noteDao.insert(note)
        }
    }

    suspend fun update(note: Note) {
        withContext(IO) {
            noteDao.update(note)
        }
    }

    suspend fun delete(note: Note) {
        withContext(IO) {
            noteDao.delete(note)
        }
    }

    suspend fun deleteAllNotes() {
        withContext(IO) {
            noteDao.deleteAllNotes()
        }
    }

    fun getNotes() = noteDao.getNotes()

}