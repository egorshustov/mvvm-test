package com.egorshustov.mvvmtest.addnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.egorshustov.mvvmtest.data.Note
import com.egorshustov.mvvmtest.data.source.NotesRepository

class AddNoteViewModel(context: Application) : AndroidViewModel(context) {
    // The idea is to have one viewmodel per view,
    // i.e one viewmodel for each activity/fragment in the activity.
    // We need the application context to instantiate the repository
    // and we will only get it from AndroidViewModel:
    private val repository: NotesRepository = NotesRepository(context)

    fun insert(note: Note) {
        repository.insert(note)
    }
}