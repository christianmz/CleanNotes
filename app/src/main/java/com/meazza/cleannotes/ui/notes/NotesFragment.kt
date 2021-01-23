package com.meazza.cleannotes.ui.notes

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.meazza.cleannotes.R
import com.meazza.cleannotes.business.vo.Status
import com.meazza.cleannotes.databinding.FragmentNotesBinding
import com.meazza.cleannotes.util.Constants.KEY_LOGGED_IN_EMAIL
import com.meazza.cleannotes.util.hideKeyboard
import com.meazza.cleannotes.util.isThereAnInternetConnection
import com.meazza.cleannotes.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {

    @Inject
    lateinit var prefs: SharedPreferences

    private val notesViewModel by viewModels<NotesViewModel>()

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val currentEmail = prefs.getString(KEY_LOGGED_IN_EMAIL, "")

        if (currentEmail.isNullOrEmpty()) {
            gotoAuth()
        }

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

        binding.swlNotes.setOnRefreshListener {
            notesViewModel.syncAllNotes()
        }

        getNotes()
        hideKeyboard(requireActivity())
    }

    private fun getNotes() {
        notesViewModel.run {
            getAllNotes(isThereAnInternetConnection(requireContext())).observe(viewLifecycleOwner, {
                lifecycleScope.launchWhenStarted {
                    it.collect { result ->
                        when (result.status) {
                            Status.SUCCESS -> {
                                adapter.notes = result.data
                                binding.swlNotes.isRefreshing = false
                            }
                            Status.ERROR -> {
                                result.run {
                                    message?.let { message ->
                                        toast(message)
                                    }

                                    data?.let { notes ->
                                        adapter.notes = notes
                                    }
                                }
                                binding.swlNotes.isRefreshing = false
                            }
                            Status.LOADING -> {
                                result.data?.let { notes ->
                                    adapter.notes = notes
                                }
                                binding.swlNotes.isRefreshing = true
                            }
                        }
                    }
                }
            })
        }
    }

    private fun gotoAuth() {
        findNavController().navigate(
            R.id.action_global_welcome,
            null,
            NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mn_dark_mode -> {

            }
            R.id.mn_logout -> {
                prefs.edit().putString(KEY_LOGGED_IN_EMAIL, "").apply()
                gotoAuth()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}