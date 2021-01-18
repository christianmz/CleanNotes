package com.meazza.cleannotes.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    fun gotoNotes() {

    }

    private fun signIn() {

    }

    private fun signUp() {

    }
}