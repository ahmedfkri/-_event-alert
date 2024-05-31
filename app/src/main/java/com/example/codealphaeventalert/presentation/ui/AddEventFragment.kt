package com.example.codealphaeventalert.presentation.ui

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.codealphaeventalert.R
import com.example.codealphaeventalert.core.util.AlarmReceiver
import com.example.codealphaeventalert.databinding.FragmentAddEventBinding
import com.example.codealphaeventalert.domain.model.Event
import com.example.codealphaeventalert.domain.model.InvalidEventException
import com.example.codealphaeventalert.presentation.view_model.EventViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AddEventFragment : Fragment() {

    lateinit var binding: FragmentAddEventBinding
    lateinit var viewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        var dateTime = ""

        binding.edtDate.setOnClickListener {
            showDateTimePicker { value ->
                binding.edtDate.setText(value)
                dateTime = value
            }
        }

        binding.btnSave.setOnClickListener {
            try {


                val title = binding.edtTitle.text.toString()
                val description = binding.edtDescription.text.toString()


                val event = Event(
                    title = title,
                    dateTime = dateTime,
                    description = description,
                )

                lifecycleScope.launch {
                    viewModel.addEvent(event)
                    findNavController().navigateUp()
                }
            } catch (e: InvalidEventException) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(activity, "Unexpected error occurred", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun showDateTimePicker(onDateTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val selectedDateTime = dateTimeFormat.format(calendar.time)
                onDateTimeSelected(selectedDateTime)
                setReminderAlarm(
                    calendar.timeInMillis,
                    selectedDateTime.hashCode()
                ) // Set reminder alarm here with a unique request code
            }

            TimePickerDialog(
                requireContext(), timeSetListener,
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE],
                true
            ).show()
        }

        DatePickerDialog(
            requireContext(), dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setReminderAlarm(timeInMillis: Long, requestCode: Int) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        // Include the event ID or other unique identifier as an extra in the intent
        intent.putExtra("requestCode", requestCode)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

}