package com.meazza.cleannotes.ui.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.meazza.cleannotes.R
import com.meazza.cleannotes.util.hideKeyboard

class NotesFragment : Fragment(R.layout.fragment_notes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideKeyboard(requireActivity())
    }
}