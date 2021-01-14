package com.meazza.cleannotes.data.network.service

import com.meazza.cleannotes.data.network.dto.NoteDto
import com.meazza.cleannotes.data.network.request.AddOwnerRequest
import com.meazza.cleannotes.data.network.request.DeleteNoteRequest
import com.meazza.cleannotes.data.network.response.SimpleResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KtorNotesService {

    @POST("/addNote")
    suspend fun addNote(@Body note: NoteDto): Response<ResponseBody>

    @POST("/deleteNote")
    suspend fun deleteNote(@Body deleteNoteRequest: DeleteNoteRequest): Response<ResponseBody>

    @POST("/addOwnerToNote")
    suspend fun addOwnerToNote(@Body addOwnerRequest: AddOwnerRequest): Response<SimpleResponse>

    @GET("/getNotes")
    suspend fun getNotes(): Response<List<NoteDto>>
}