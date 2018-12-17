package com.egorshustov.mvvmtest.notes

import androidx.lifecycle.LiveData
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.egorshustov.mvvmtest.data.source.AppRepository
import com.egorshustov.mvvmtest.data.Note


class NotesViewModel(application: Application) : AndroidViewModel(application) {
    // The idea is to have one viewmodel per view,
    // i.e one viewmodel for each activity/fragment in the activity.
    private val repository: AppRepository
    private val allNotes: LiveData<List<Note>?>

    init {
        // We need the application context to instantiate the repository
        // and we will only get it from AndroidViewModel
        repository = AppRepository(application)
        allNotes = repository.getAllNotes()
    }

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>?> {
        return allNotes
    }
}