package com.meazza.cleannotes.ui.upsert_note

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.meazza.cleannotes.R
import com.meazza.cleannotes.databinding.FragmentUpsertNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpsertNoteFragment : Fragment(R.layout.fragment_upsert_note) {

    private val upsertViewModel by viewModels<UpsertNoteViewModel>()

    private var _binding: FragmentUpsertNoteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentUpsertNoteBinding.bind(view).apply {
            lifecycleOwner = this@UpsertNoteFragment
            viewModel = upsertViewModel
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mn_share -> {

            }
            R.id.mn_delete -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}