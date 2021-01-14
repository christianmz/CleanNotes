package com.meazza.cleannotes.data.network.service

import com.meazza.cleannotes.data.network.request.AccountRequest
import com.meazza.cleannotes.data.network.response.SimpleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface KtorNotesAuthService {

    @POST("/register")
    suspend fun register(@Body registerRequest: AccountRequest): Response<SimpleResponse>

    @POST("/login")
    suspend fun login(@Body loginRequest: AccountRequest): Response<SimpleResponse>
}