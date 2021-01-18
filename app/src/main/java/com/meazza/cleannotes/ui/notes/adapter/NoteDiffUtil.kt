package com.meazza.cleannotes.ui.notes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.meazza.cleannotes.business.domain.Note

object NoteDiffUtil : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
}

