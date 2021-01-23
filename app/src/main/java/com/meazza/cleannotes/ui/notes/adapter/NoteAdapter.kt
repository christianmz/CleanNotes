package com.meazza.cleannotes.ui.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.databinding.LayoutNoteBinding

object NoteAdapter : ListAdapter<Note, NoteAdapter.NoteHolder>(NoteDiffUtil) {

    private val differ = AsyncListDiffer(this, NoteDiffUtil)

    var notes: List<Note>?
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteHolder(
        LayoutNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.itemBinding.run {
            note = currentList[position]
            executePendingBindings()
        }
    }

    class NoteHolder(val itemBinding: LayoutNoteBinding) : RecyclerView.ViewHolder(itemBinding.root)
}