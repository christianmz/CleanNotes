package com.meazza.cleannotes.business.domain

data class Note(
    val title: String,
    val content: String,
    val date: Long,
    val owners: List<String?>,
    var isSynced: Boolean = false,
    val id: String
)
