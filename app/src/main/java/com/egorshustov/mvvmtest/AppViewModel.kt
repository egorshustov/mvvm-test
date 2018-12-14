package com.egorshustov.mvvmtest

import androidx.lifecycle.LiveData
import android.app.Application
import androidx.lifecycle.AndroidViewModel


class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    private val allNotes: LiveData<List<Note>>

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

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}