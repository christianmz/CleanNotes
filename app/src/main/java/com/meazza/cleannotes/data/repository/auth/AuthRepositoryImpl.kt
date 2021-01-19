package com.meazza.cleannotes.data.repository.auth

import com.meazza.cleannotes.business.repository.AuthRepository
import com.meazza.cleannotes.data.repository.auth.datasource.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun register(email: String, password: String) =
        authService.register(email, password)

    override suspend fun login(email: String, password: String) =
        authService.login(email, password)
}