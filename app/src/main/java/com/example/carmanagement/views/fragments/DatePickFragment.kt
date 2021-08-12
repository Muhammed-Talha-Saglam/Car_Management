package com.example.carmanagement.views.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carmanagement.R
import com.example.carmanagement.databinding.FragmentDatePickBinding
import com.example.carmanagement.viewModels.MainViewModel
import java.time.LocalDateTime

class DatePickFragment : Fragment(R.layout.fragment_date_pick) {


    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentDatePickBinding

    lateinit var startDatePickerDialog: DatePickerDialog
    lateinit var endDatePickerDialog: DatePickerDialog

    lateinit var startTimePickerDialog: TimePickerDialog
    lateinit var endTimePickerDialog: TimePickerDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDatePickBinding.bind(view)

        initStartDatePicker()
        initEndDatePicker()

        initStartTimePicker()
        initEndTimePicker()

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

        viewModel.startDate.observe(viewLifecycleOwner, { startDate ->
            val day = startDate.dayOfMonth
            val month = getMonthFormat(startDate.monthValue)
            val year = startDate.year
            val hour = startDate.hour
            val minute = startDate.minute

            val startDateText = "$day $month $year"
            val startTimeText = "$hour $minute"
            binding.buttonStartDate.text = startDateText
            binding.buttonStartTime.text = startTimeText
        }
        )

        viewModel.endDate.observe(viewLifecycleOwner, { endDate ->
            val day = endDate.dayOfMonth
            val month = getMonthFormat(endDate.monthValue)
            val year = endDate.year
            val hour = endDate.hour
            val minute = endDate.minute

            val endDateText = "$day $month $year"
            val endTimeText = "$hour $minute"
            binding.buttonEndDate.text = endDateText
            binding.buttonEndTime.text = endTimeText
        })


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
                viewModel.setStartTime(hourOfDay, minute)
            }
        startTimePickerDialog = TimePickerDialog(requireContext(), onTimeListener, 12, 0, true)
    }

    private fun initEndTimePicker() {

        val onTimeListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                Log.d("time end", "$hourOfDay : $minute")
                viewModel.setEndTime(hourOfDay, minute)
            }
        endTimePickerDialog = TimePickerDialog(requireContext(), onTimeListener, 12, 0, true)
    }


    private fun initStartDatePicker() {

        startDatePickerDialog = DatePickerDialog(requireContext())
        startDatePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            Log.d("startPicker", "$year - $month - $dayOfMonth")
            // First month is 0, so we add 1
            viewModel.setStartDate(year, month + 1, dayOfMonth)
        }
    }

    private fun initEndDatePicker() {
        endDatePickerDialog = DatePickerDialog(requireContext())
        endDatePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            Log.d("endPicker", "$year - $month - $dayOfMonth")
            // First month is 0, so we add 1
            viewModel.setEndDate(year, month + 1, dayOfMonth)
        }
    }


    private fun openStartDatePicker() {
        startDatePickerDialog.show()
    }

    private fun openEndDatePicker() {
        endDatePickerDialog.show()
    }

    private fun getTodaysDate() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        var month = calender.get(Calendar.MONTH) + 1
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val hour = calender.time.hours
        val minute = calender.time.minutes

        viewModel.startDate.value = LocalDateTime.of(year, month, day, hour, minute)
        viewModel.endDate.value = LocalDateTime.of(year, month, day, hour, minute)


    }

    private fun getMonthFormat(month: Int): String {


       return android.icu.text.DateFormatSymbols.getInstance().months[month-1]

    }

    private fun goToCarListPage() {
        val action = DatePickFragmentDirections.actionDatePickFragmentToCarListFragment()
        findNavController().navigate(action)
    }

}