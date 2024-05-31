package com.example.codealphaeventalert.domain.repository


import com.example.codealphaeventalert.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getAllEvents(): Flow<List<Event>>

    suspend fun getEventById(eventId: Int): Event?

    suspend fun addEvent(event: Event)

    suspend fun deleteEvent(event: Event)
}