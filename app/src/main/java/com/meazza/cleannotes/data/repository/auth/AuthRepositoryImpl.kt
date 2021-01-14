package com.meazza.cleannotes.data.repository.auth

import com.meazza.cleannotes.business.repository.AuthRepository
import com.meazza.cleannotes.data.repository.auth.datasource.RemoteAuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource
) : AuthRepository {

    override suspend fun register(email: String, password: String) {
        remoteAuthDataSource.register(email, password)
    }

    override suspend fun login(email: String, password: String) {
        remoteAuthDataSource.login(email, password)
    }
}