package com.egorshustov.mvvmtest

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import android.app.Application


class AppRepository(application: Application) {
    private val noteDao: NoteDao
    private val allNotes: LiveData<List<Note>>

    init {
        val database = AppDatabase.getInstance(application)
        noteDao = database!!.noteDao()
        allNotes = noteDao.allNotes
    }

    // These methods are the API that AppRepository exposes
    // to the outside:
    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(noteDao).execute()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    // AsyncTasks has to be static companion objects
    // (must not have a reference to repository itself,
    // otherwise this could cause a memory leak):
    companion object {

        private class InsertNoteAsyncTask
        internal constructor(private val noteDao: NoteDao) :
            AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg notes: Note): Void? {
                noteDao.insert(notes[0])
                return null
            }
        }

        private class UpdateNoteAsyncTask
        internal constructor(private val noteDao: NoteDao) :
            AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg notes: Note): Void? {
                noteDao.update(notes[0])
                return null
            }
        }

        private class DeleteNoteAsyncTask
        internal constructor(private val noteDao: NoteDao) :
            AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg notes: Note): Void? {
                noteDao.delete(notes[0])
                return null
            }
        }

        private class DeleteAllNotesAsyncTask
        internal constructor(private val noteDao: NoteDao) :
            AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                noteDao.deleteAllNotes()
                return null
            }
        }
    }
}