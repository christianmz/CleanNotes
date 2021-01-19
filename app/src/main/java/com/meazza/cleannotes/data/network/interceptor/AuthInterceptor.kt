package com.meazza.cleannotes.data.network.interceptor

import com.meazza.cleannotes.data.util.Constants.IGNORE_AUTH_URLS
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

object AuthInterceptor : Interceptor {

    var email: String? = null
    var password: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val authenticatedRequest = request.newBuilder()
            .header("Authorization", Credentials.basic(email ?: "", password ?: ""))
            .build()

        if (request.url.encodedPath in IGNORE_AUTH_URLS) {
            return chain.proceed(request)
        }

        return chain.proceed(authenticatedRequest)
    }
}
