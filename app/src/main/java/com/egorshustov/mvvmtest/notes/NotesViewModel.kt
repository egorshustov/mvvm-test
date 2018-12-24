package com.egorshustov.mvvmtest.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.egorshustov.mvvmtest.data.Note
import com.egorshustov.mvvmtest.data.source.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class NotesViewModel(context: Application) : AndroidViewModel(context) {
    // The idea is to have one ViewModel per view,
    // i.e one ViewModel for each activity/fragment in the activity.
    // We need the application context to instantiate the repository
    // and we will only get it from AndroidViewModel:
    private val repository: NotesRepository = NotesRepository(context)
    val notes = repository.getNotes()

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * This is the scope for all coroutines launched by [NotesViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }
}