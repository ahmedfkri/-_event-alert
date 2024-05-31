package com.example.codealphaeventalert.domain.use_case

import com.example.codealphaeventalert.data.repository.EventRepositoryImpl
import com.example.codealphaeventalert.domain.model.Event
import com.example.codealphaeventalert.domain.model.InvalidEventException
import kotlin.jvm.Throws

class AddEventUseCase (
    private val repository: EventRepositoryImpl
        ){

    @Throws(InvalidEventException::class)
    suspend operator fun invoke(event: Event){
        if(event.title.isBlank()) throw InvalidEventException("Title is empty")
        repository.addEvent(event)
    }
}