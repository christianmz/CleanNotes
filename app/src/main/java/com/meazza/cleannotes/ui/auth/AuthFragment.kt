package com.meazza.cleannotes.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.meazza.cleannotes.R
import com.meazza.cleannotes.business.vo.Status
import com.meazza.cleannotes.databinding.FragmentAuthBinding
import com.meazza.cleannotes.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val authViewModel by viewModels<AuthViewModel>()

    private var _binding: FragmentAuthBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAuthBinding.bind(view).apply {
            lifecycleOwner = this@AuthFragment
            viewModel = authViewModel
        }

        signUpHandler()
        signInHandler()
    }

    private fun signUpHandler() {
        authViewModel.run {
            authResult.observe(viewLifecycleOwner, { result ->
                result?.let {
                    when (result.status) {
                        Status.SUCCESS -> {
                            isVisibleProgressBar.value = false
                            toast(result.data ?: "Successfully registered an account")
                            findNavController().navigate(R.id.nav_notes)
                        }
                        Status.ERROR -> {
                            isVisibleProgressBar.value = false
                            toast(result.message ?: "An unknown error occurred")
                        }
                        Status.LOADING -> {
                            isVisibleProgressBar.value = true
                        }
                    }
                }
            })
        }
    }

    private fun signInHandler() {
        authViewModel.run {
            authResult.observe(viewLifecycleOwner, { result ->
                result?.let {
                    when (result.status) {
                        Status.SUCCESS -> {
                            isVisibleProgressBar.value = false
                            toast(result.data ?: "Welcome")
                            findNavController().navigate(R.id.nav_notes)
                        }
                        Status.ERROR -> {
                            isVisibleProgressBar.value = false
                            toast(result.message ?: "An unknown error occurred")
                        }
                        Status.LOADING -> {
                            isVisibleProgressBar.value = true
                        }
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}