package com.meazza.cleannotes.data.network.request

data class AddOwnerRequest(
    val owner: String,
    val noteId: String
)
