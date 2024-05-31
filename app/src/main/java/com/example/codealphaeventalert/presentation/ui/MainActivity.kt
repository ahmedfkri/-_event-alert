package com.example.codealphaeventalert.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codealphaeventalert.R
import com.example.codealphaeventalert.data.local.EventDatabase
import com.example.codealphaeventalert.data.repository.EventRepositoryImpl
import com.example.codealphaeventalert.databinding.ActivityMainBinding
import com.example.codealphaeventalert.domain.use_case.AddEventUseCase
import com.example.codealphaeventalert.domain.use_case.EventUseCases
import com.example.codealphaeventalert.domain.use_case.GetEventsUseCase
import com.example.codealphaeventalert.presentation.view_model.EventViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = EventRepositoryImpl(EventDatabase(this))

        val addEventUseCase = AddEventUseCase(repository)
        val getAllEventUseCase = GetEventsUseCase(repository)
        val eventUseCases = EventUseCases(addEventUseCase, getAllEventUseCase)
        viewModel = EventViewModel(eventUseCases)
    }
}