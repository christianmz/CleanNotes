package com.meazza.cleannotes.ui.auth

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meazza.cleannotes.business.repository.AuthRepository
import com.meazza.cleannotes.business.vo.Resource
import com.meazza.cleannotes.util.Constants.KEY_LOGGED_IN_EMAIL
import com.meazza.cleannotes.util.isValidEmail
import com.meazza.cleannotes.util.isValidPassword
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    private val prefs: SharedPreferences
) : ViewModel() {

    private val _authResult = MutableLiveData<Resource<String>>()
    val authResult: LiveData<Resource<String>> get() = _authResult

    val userEmail = MutableLiveData<String>()
    val userPassword = MutableLiveData<String>()
    val userConfirmPassword = MutableLiveData<String>()
    val isVisibleProgressBar = MutableLiveData<Boolean>()
    val isChecked = MutableLiveData<Boolean>()

    fun gotoNotes() {
        if (isChecked.value == true) signIn() else signUp()
    }

    private fun signUp() {

        val email = userEmail.value
        val password = userPassword.value

        _authResult.postValue(Resource.loading(null))

        viewModelScope.launch {
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                when {
                    !isValidEmail(email) -> _authResult.postValue(
                        Resource.error(
                            "Invalid email",
                            null
                        )
                    )
                    !isValidPassword(password) -> _authResult.postValue(
                        Resource.error(
                            "Weak password",
                            null
                        )
                    )
                    isValidEmail(email) && isValidPassword(password) -> {
                        val result = authRepository.login(email, password)
                        _authResult.postValue(result)
                        prefs.edit().putString(KEY_LOGGED_IN_EMAIL, email).apply()
                    }
                }
            } else {
                _authResult.postValue(Resource.error("Please fill out all the fields", null))
            }
        }
    }

    private fun signIn() {

        val email = userEmail.value
        val password = userPassword.value
        val confirmPassword = userConfirmPassword.value

        _authResult.postValue(Resource.loading(null))

        viewModelScope.launch {
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !confirmPassword.isNullOrEmpty()) {
                when {
                    password != confirmPassword -> _authResult.postValue(
                        Resource.error(
                            "The passwords do not match",
                            null
                        )
                    )
                    !isValidEmail(email) -> _authResult.postValue(
                        Resource.error(
                            "Invalid email",
                            null
                        )
                    )
                    !isValidPassword(password) -> _authResult.postValue(
                        Resource.error(
                            "Weak password",
                            null
                        )
                    )
                    isValidEmail(email) && isValidPassword(password) -> {
                        val result = authRepository.register(email, password)
                        _authResult.postValue(result)
                        prefs.edit().putString(KEY_LOGGED_IN_EMAIL, email).apply()
                    }
                }
            } else {
                _authResult.postValue(Resource.error("Please fill out all the fields", null))
            }
        }
    }
}
