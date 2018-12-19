package com.egorshustov.mvvmtest.data.source

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import android.app.Application
import com.egorshustov.mvvmtest.data.source.local.AppDatabase
import com.egorshustov.mvvmtest.data.source.local.NotesDao
import com.egorshustov.mvvmtest.data.Note


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
        InsertNoteAsyncTask(notesDao).execute(note)
    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(notesDao).execute(note)
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(notesDao).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(notesDao).execute()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    // AsyncTasks has to be static companion objects
    // (must not have a reference to repository itself,
    // otherwise this could cause a memory leak):
    companion object {

        private class InsertNoteAsyncTask
        internal constructor(private val notesDao: NotesDao) :
            AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg notes: Note): Void? {
                notesDao.insert(notes[0])
                return null
            }
        }

        private class UpdateNoteAsyncTask
        internal constructor(private val notesDao: NotesDao) :
            AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg notes: Note): Void? {
                notesDao.update(notes[0])
                return null
            }
        }

        private class DeleteNoteAsyncTask
        internal constructor(private val notesDao: NotesDao) :
            AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg notes: Note): Void? {
                notesDao.delete(notes[0])
                return null
            }
        }

        private class DeleteAllNotesAsyncTask
        internal constructor(private val notesDao: NotesDao) :
            AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                notesDao.deleteAllNotes()
                return null
            }
        }
    }
}