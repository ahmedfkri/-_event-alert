package com.example.codealphaeventalert.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codealphaeventalert.domain.model.Event
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {

    @Query("Select * From eventsTable")
    fun getAllEvents(): Flow<List<Event>>

    @Query("Select * from eventsTable Where id = :eventId")
    suspend fun getEventById(eventId: Int): Event?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

}