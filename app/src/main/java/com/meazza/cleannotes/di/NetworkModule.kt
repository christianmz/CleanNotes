package com.meazza.cleannotes.di

import com.meazza.cleannotes.data.network.interceptor.AuthInterceptor
import com.meazza.cleannotes.data.network.service.KtorNotesAuthService
import com.meazza.cleannotes.data.network.service.KtorNotesService
import com.meazza.cleannotes.data.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object NetworkModule {

    @ActivityRetainedScoped
    @Provides
    fun providesAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor
    }

    @ActivityRetainedScoped
    @Provides
    fun providesRetrofitInstance(
        interceptor: AuthInterceptor
    ): Retrofit.Builder {

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
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