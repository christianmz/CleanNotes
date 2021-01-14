package com.meazza.cleannotes.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.meazza.cleannotes.data.network.service.KtorNotesAuthService
import com.meazza.cleannotes.data.network.service.KtorNotesService
import com.meazza.cleannotes.util.Constants
import com.meazza.cleannotes.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object NetworkModule {

    @ActivityRetainedScoped
    @Provides
    fun providesGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @ActivityRetainedScoped
    @Provides
    fun providesGsonFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @ActivityRetainedScoped
    @Provides
    fun providesAuthInterceptor() = Interceptor { chain ->

        val email: String? = null
        val password: String? = null

        val request = chain.request()

        val authenticatedRequest = request.newBuilder()
            .header("Authorization", Credentials.basic(email ?: "", password ?: ""))
            .build()

        if (request.url.encodedPath in Constants.IGNORE_AUTH_URLS) {
            return@Interceptor chain.proceed(request)
        }

        return@Interceptor chain.proceed(authenticatedRequest)
    }

    @ActivityRetainedScoped
    @Provides
    fun providesHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @ActivityRetainedScoped
    @Provides
    fun providesRetrofitInstance(
        factory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit.Builder {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .client(client)
    }

    @ActivityRetainedScoped
    @Provides
    fun providesAuthService(retrofitBuilder: Retrofit.Builder): KtorNotesAuthService {
        return retrofitBuilder.build().create(KtorNotesAuthService::class.java)
    }

    @ActivityRetainedScoped
    @Provides
    fun providesNotesService(retrofitBuilder: Retrofit.Builder): KtorNotesService {
        return retrofitBuilder.build().create(KtorNotesService::class.java)
    }
}