package com.egorshustov.mvvmtest.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egorshustov.mvvmtest.R
import com.egorshustov.mvvmtest.data.Note

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteHolder>() {

    private val notes: ArrayList<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_note, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes.get(position)
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description
        holder.textViewPriority.text = currentNote.priority.toString()
    }

    override fun getItemCount(): Int = notes.size

    fun getNoteAt(position: Int): Note {
        return notes[position]
    }

    fun replaceNotes(refreshNotes: List<Note>) {
        notes.clear()
        notes.addAll(refreshNotes)
    }

    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textViewDescription: TextView = itemView.findViewById(R.id.text_view_description)
        val textViewPriority: TextView = itemView.findViewById(R.id.text_view_priority)
    }
}