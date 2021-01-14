package com.meazza.cleannotes.business.domain

data class Note(
    val title: String,
    val content: String,
    val date: Long,
    val owners: List<String>,
    val color: String,
    var isSynced: Boolean,
    val id: String
)
