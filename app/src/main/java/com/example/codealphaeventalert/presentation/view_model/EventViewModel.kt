package com.example.codealphaeventalert.presentation.view_model

import androidx.lifecycle.ViewModel
import com.example.codealphaeventalert.domain.model.Event
import com.example.codealphaeventalert.domain.use_case.AddEventUseCase
import com.example.codealphaeventalert.domain.use_case.EventUseCases
import com.example.codealphaeventalert.domain.use_case.GetEventsUseCase
import kotlinx.coroutines.flow.Flow

class EventViewModel(
    private val eventUseCases: EventUseCases
) : ViewModel() {

    suspend fun addEvent(event: Event) {
        eventUseCases.addEventUseCase(event)
    }

    fun getAllEvents(): Flow<List<Event>> {
        return eventUseCases.getAllEventsUseCase()
    }

}