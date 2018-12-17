package com.egorshustov.mvvmtest.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.egorshustov.mvvmtest.data.Note
import com.egorshustov.mvvmtest.data.source.AppRepository


class NotesViewModel(context: Application) : AndroidViewModel(context) {
    // The idea is to have one viewmodel per view,
    // i.e one viewmodel for each activity/fragment in the activity.
    // We need the application context to instantiate the repository
    // and we will only get it from AndroidViewModel:
    private val repository: AppRepository = AppRepository(context)
    private val allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}