package com.example.codealphaeventalert.data.repository

import com.example.codealphaeventalert.data.local.EventDatabase
import com.example.codealphaeventalert.domain.model.Event
import com.example.codealphaeventalert.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(private val db: EventDatabase) : EventRepository {

    override fun getAllEvents(): Flow<List<Event>> {
        return db.getDao().getAllEvents()
    }

    override suspend fun getEventById(eventId: Int): Event? {
        return db.getDao().getEventById(eventId)
    }

    override suspend fun addEvent(event: Event) {
        return db.getDao().addEvent(event)
    }

    override suspend fun deleteEvent(event: Event) {
        return db.getDao().deleteEvent(event)
    }
}