package com.example.codealphaeventalert.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.codealphaeventalert.R
import com.example.codealphaeventalert.databinding.FragmentEventsListBinding
import com.example.codealphaeventalert.presentation.adapter.EventsAdapter
import com.example.codealphaeventalert.presentation.view_model.EventViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EventsListFragment : Fragment() {

    private lateinit var binding: FragmentEventsListBinding
    lateinit var viewModel: EventViewModel

    lateinit var eventAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        binding.fabAddEvent.setOnClickListener {
            findNavController().navigate(R.id.action_eventsListFragment_to_addEventFragment)
        }

        lifecycleScope.launch {
            viewModel.getAllEvents().collect { events ->
                if (events.isEmpty()) {
                    Toast.makeText(requireContext(), "No Events", Toast.LENGTH_SHORT).show()
                } else {
                    eventAdapter.differ.submitList(events)
                }
            }
        }

    }

    private fun setupRecyclerView() {
        eventAdapter = EventsAdapter()
        binding.rvEvents.apply {
            adapter = eventAdapter
        }
    }

}