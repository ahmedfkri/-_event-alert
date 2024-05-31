package com.example.codealphaeventalert.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Exception

@Entity(tableName = "eventsTable")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val dateTime: String,
    val description: String
)

class InvalidEventException(message: String) : Exception(message)