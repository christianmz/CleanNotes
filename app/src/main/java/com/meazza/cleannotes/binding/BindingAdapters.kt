package com.meazza.cleannotes.binding

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.meazza.cleannotes.R
import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.parcelize.NoteParcelize
import com.meazza.cleannotes.ui.notes.NotesFragmentDirections
import com.meazza.cleannotes.ui.notes.adapter.NoteAdapter
import io.noties.markwon.Markwon
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:setVisibility")
    fun setVisibility(view: View, isVisible: Boolean) {
        when (isVisible) {
            true -> view.visibility = View.VISIBLE
            false -> view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("android:setCustomAdapter")
    fun setCustomAdapter(recyclerView: RecyclerView, noteAdapter: NoteAdapter) {
        recyclerView.run {
            setHasFixedSize(true)
            adapter = noteAdapter
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
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

    @JvmStatic
    @BindingAdapter("android:setMarkDownText")
    fun setMarkdownText(textView: TextView, text: String) {
        val markwon = Markwon.create(textView.context)
        val markdown = markwon.toMarkdown(text)
        markwon.setParsedMarkdown(textView, markdown)
    }
}