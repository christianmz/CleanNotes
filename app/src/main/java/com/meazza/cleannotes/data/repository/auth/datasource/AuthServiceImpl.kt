package com.meazza.cleannotes.data.repository.auth.datasource

import com.meazza.cleannotes.business.vo.Resource
import com.meazza.cleannotes.data.network.interceptor.AuthInterceptor
import com.meazza.cleannotes.data.network.request.AccountRequest
import com.meazza.cleannotes.data.network.service.KtorNotesAuthService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val authService: KtorNotesAuthService,
    private val authInterceptor: AuthInterceptor
) : AuthService {

    override suspend fun register(email: String, password: String) = withContext(IO) {
        try {

            val response = authService.register(AccountRequest(email, password))

            if (response.isSuccessful && response.body()!!.isSuccessful) {
                authInterceptor.email = email
                authInterceptor.password = password
                Resource.success(response.body()?.message)
            } else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }

        } catch (e: Exception) {
            Resource.error(
                "Couldn't connect to the servers. Check your internet connection",
                null
            )
        }
    }

    override suspend fun login(email: String, password: String) = withContext(IO) {
        try {

            val response = authService.login(AccountRequest(email, password))

            if (response.isSuccessful && response.body()!!.isSuccessful) {
                Resource.success(response.body()?.message)
            } else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }

        } catch (e: Exception) {
            Resource.error(
                "Couldn't connect to the servers. Check your internet connection",
                null
            )
        }
    }
}