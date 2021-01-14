package com.meazza.cleannotes.data.repository.auth.datasource

interface RemoteAuthDataSource {
    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
}