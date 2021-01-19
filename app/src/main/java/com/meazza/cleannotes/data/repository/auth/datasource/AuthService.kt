package com.meazza.cleannotes.data.repository.auth.datasource

import com.meazza.cleannotes.business.vo.Resource

interface AuthService {
    suspend fun register(email: String, password: String): Resource<String>
    suspend fun login(email: String, password: String): Resource<String>
}