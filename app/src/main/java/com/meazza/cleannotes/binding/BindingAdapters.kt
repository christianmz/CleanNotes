package com.meazza.cleannotes.binding

import android.view.View
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.meazza.cleannotes.R
import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.parcelize.NoteParcelize
import com.meazza.cleannotes.ui.notes.NotesFragmentDirections
import com.meazza.cleannotes.ui.notes.adapter.NoteAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:isEmptyList")
    fun isEmptyList(view: View, isEmptyList: Boolean) {
        when (isEmptyList) {
            true -> view.visibility = View.VISIBLE
            false -> view.visibility = View.INVISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("android:setCustomAdapter")
    fun setCustomAdapter(recyclerView: RecyclerView, noteAdapter: NoteAdapter) {
        recyclerView.adapter = noteAdapter
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
    }

    @JvmStatic
    @BindingAdapter("android:setColorIfSynced")
    fun setColorIfSynced(view: View, isSynced: Boolean) {
        if (isSynced)
            view.run {
                setBackgroundColor(context.getColor(R.color.green))
            }
        else
            view.run {
                setBackgroundColor(context.getColor(R.color.red))
            }
    }

    @JvmStatic
    @BindingAdapter("android:onNoteClick")
    fun onNoteClick(card: CardView, note: Note) {
        card.setOnClickListener {
            val action = NotesFragmentDirections.actionUpsertNote(NoteParcelize.fromNote(note))
            card.findNavController().navigate(action)
        }
    }
}