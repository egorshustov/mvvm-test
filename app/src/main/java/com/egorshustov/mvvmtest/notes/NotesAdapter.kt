package com.egorshustov.mvvmtest.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egorshustov.mvvmtest.R
import com.egorshustov.mvvmtest.data.Note


class NotesAdapter : ListAdapter<Note, NotesAdapter.NoteHolder> {

    //private val notes: ArrayList<Note> = ArrayList()
    var listener: OnItemClickListener? = null

    constructor() : super(DIFF_CALLBACK) {
        //this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description
        holder.textViewPriority.text = currentNote.priority.toString()
    }

    /*override fun getItemCount(): Int = notes.size*/

    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    /*fun replaceNotes(refreshNotes: List<Note>) {
        notes.clear()
        notes.addAll(refreshNotes)
    }*/

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView
        val textViewDescription: TextView
        val textViewPriority: TextView

        init {
            textViewTitle = itemView.findViewById(R.id.text_view_title)
            textViewDescription = itemView.findViewById(R.id.text_view_description)
            textViewPriority = itemView.findViewById(R.id.text_view_priority)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position) /*Let's pass notes[position] argument to onItemClick method*/)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener /*we get passed this argument (listener)*/) {
        this.listener = listener
        // We can later use listener class member variable to call our OnItemClick method on it,
        // and this way forward our note object to whatever implements this interface.
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.description == newItem.description &&
                        oldItem.priority == newItem.priority
            }
        }
    }
}