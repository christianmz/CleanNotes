package com.meazza.cleannotes.ui.notes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.meazza.cleannotes.R
import com.meazza.cleannotes.databinding.FragmentNotesBinding
import com.meazza.cleannotes.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {

    private val notesViewModel by viewModels<NotesViewModel>()

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNotesBinding.bind(view).apply {
            lifecycleOwner = this@NotesFragment
            viewModel = notesViewModel
        }

        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_upsert_note)
        }

        hideKeyboard(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_settings) {
            findNavController().navigate(
                R.id.action_global_welcome, null,
                NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}