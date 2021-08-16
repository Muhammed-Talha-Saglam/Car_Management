package com.example.carmanagement.views.fragments.datePickerPage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carmanagement.R
import com.example.carmanagement.constants.toStringDate
import com.example.carmanagement.constants.toStringTime
import com.example.carmanagement.databinding.FragmentDatePickBinding
import com.example.carmanagement.model.User
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.ZoneOffset

@AndroidEntryPoint
class DatePickFragment : Fragment(R.layout.fragment_date_pick) {


    private val datePickViewModel: DatePickViewModel by viewModels()

    lateinit var binding: FragmentDatePickBinding

    lateinit var startDatePickerDialog: DatePickerDialog
    lateinit var endDatePickerDialog: DatePickerDialog

    lateinit var startTimePickerDialog: TimePickerDialog
    lateinit var endTimePickerDialog: TimePickerDialog

    lateinit var user: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = arguments?.getParcelable("user")!!

        binding = FragmentDatePickBinding.bind(view)

        initStartDatePicker()
        initEndDatePicker()

        initStartTimePicker()
        initEndTimePicker()

        // Set initial date values
        getTodaysDate()


        binding.apply {

            buttonStartDate.setOnClickListener {
                openStartDatePicker()
            }
            buttonEndDate.setOnClickListener {
                openEndDatePicker()
            }
            buttonStartTime.setOnClickListener {
                openStartTimePicker()
            }
            buttonEndTime.setOnClickListener {
                openEndTimePicker()
            }
            buttonFilter.setOnClickListener {
                goToCarListPage()
            }

        }

        datePickViewModel.startLocalDateTime.observe(viewLifecycleOwner, { startDate ->

            binding.buttonStartDate.text = startDate.toStringDate()
            binding.buttonStartTime.text = startDate.toStringTime()
        }
        )

        datePickViewModel.endLocalDateTime.observe(viewLifecycleOwner, { endDate ->

            binding.buttonEndDate.text = endDate.toStringDate()
            binding.buttonEndTime.text = endDate.toStringTime()
        })


    }


    private fun openStartDatePicker() {
        startDatePickerDialog.show()
    }

    private fun openEndDatePicker() {
        endDatePickerDialog.show()
    }

    private fun openStartTimePicker() {
        startTimePickerDialog.show()
    }


    private fun openEndTimePicker() {
        endTimePickerDialog.show()
    }


    private fun initStartTimePicker() {

        val onTimeListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                Log.d("time start", "$hourOfDay : $minute")
                datePickViewModel.setStartTime(hourOfDay, minute)
            }
        startTimePickerDialog = TimePickerDialog(requireContext(), onTimeListener, 12, 0, true)
    }


    private fun initStartDatePicker() {

        startDatePickerDialog = DatePickerDialog(requireContext())
        startDatePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            Log.d("startPicker", "$year - $month - $dayOfMonth")
            // First month is 0, so we add 1
            datePickViewModel.setStartDate(year, month + 1, dayOfMonth)
        }
    }

    private fun initEndDatePicker() {
        endDatePickerDialog = DatePickerDialog(requireContext())
        endDatePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            Log.d("endPicker", "$year - $month - $dayOfMonth")
            // First month is 0, so we add 1
            datePickViewModel.setEndDate(year, month + 1, dayOfMonth)
        }
    }

    private fun initEndTimePicker() {

        val onTimeListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                Log.d("time end", "$hourOfDay : $minute")
                datePickViewModel.setEndTime(hourOfDay, minute)
            }
        endTimePickerDialog = TimePickerDialog(requireContext(), onTimeListener, 12, 0, true)
    }


    /*
    Set the initial date and time to the device's current date and time.
     */
    private fun getTodaysDate() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        var month = calender.get(Calendar.MONTH) + 1
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val hour = calender.time.hours
        val minute = calender.time.minutes

        datePickViewModel.startLocalDateTime.value =
            LocalDateTime.of(year, month, day, hour, minute)
        datePickViewModel.endLocalDateTime.value = LocalDateTime.of(year, month, day, hour, minute)


    }


    private fun goToCarListPage() {

        val startDate = datePickViewModel.startLocalDateTime.value!!.toEpochSecond(ZoneOffset.UTC)
        val endDate = datePickViewModel.endLocalDateTime.value!!.toEpochSecond(ZoneOffset.UTC)


        val action = DatePickFragmentDirections.actionDatePickFragmentToCarListFragment(
            user,
            startDate,
            endDate
        )


        if (isDateValid())
            findNavController().navigate(action)
        else
            Toast.makeText(requireContext(),getString(R.string.date_invalid),Toast.LENGTH_LONG).show()
    }

    private fun isDateValid(): Boolean {

        val startDate = datePickViewModel.startLocalDateTime.value!!
        val endDate = datePickViewModel.endLocalDateTime.value!!
        return startDate.isBefore(endDate)
    }

}