package com.meazza.cleannotes.data.util

object Constants {

    val IGNORE_AUTH_URLS = listOf("/login", "/register")

    const val BASE_URL = "http://10.0.2.2:8080"

    const val DATABASE_NAME = "notes_db"
    const val TABLE_NAME_NOTES = "notes"
    const val TABLE_NAME_DELETED_NOTES = "deleted_notes_id"
}