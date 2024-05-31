package com.example.codealphaeventalert.domain.use_case

import com.example.codealphaeventalert.data.repository.EventRepositoryImpl
import com.example.codealphaeventalert.domain.model.Event
import com.example.codealphaeventalert.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventsUseCase(
    private val repository: EventRepositoryImpl
) {

    operator fun invoke(): Flow<List<Event>> {
        return repository.getAllEvents()
    }

}