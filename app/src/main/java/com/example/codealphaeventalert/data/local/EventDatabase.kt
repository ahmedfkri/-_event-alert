package com.example.codealphaeventalert.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.codealphaeventalert.domain.model.Event

@Database(
    entities = [Event::class],
    version = 2
)
abstract class EventDatabase : RoomDatabase() {


    abstract fun getDao(): EventDao

    companion object {
        @Volatile
        private var instance: EventDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) =
            instance ?: synchronized(LOCK) {
                instance ?: createDatabase(context).also {
                    instance = it
                }

            }

        private fun createDatabase(context: Context): EventDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                EventDatabase::class.java,
                "events_db.db"
            ).fallbackToDestructiveMigration()
                .build()
        }

    }


}