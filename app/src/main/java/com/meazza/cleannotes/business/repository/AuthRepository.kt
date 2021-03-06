package com.meazza.cleannotes.business.repository

import com.meazza.cleannotes.business.vo.Resource

interface AuthRepository {
    suspend fun register(email: String, password: String): Resource<String>
    suspend fun login(email: String, password: String): Resource<String>
}