package com.example.carmanagement.views.fragments.datePickerPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carmanagement.constants.copyWithDate
import com.example.carmanagement.constants.copyWithHour
import java.time.LocalDateTime

class DatePickViewModel : ViewModel() {

    val startLocalDateTime = MutableLiveData<LocalDateTime>()
    val endLocalDateTime = MutableLiveData<LocalDateTime>()

    

    fun setStartDate(year: Int, month: Int, day: Int) {
        startLocalDateTime.value = startLocalDateTime.value?.copyWithHour(year, month, day)
    }

    fun setStartTime(hour: Int, minute: Int) {
        startLocalDateTime.value = startLocalDateTime.value?.copyWithDate(hour, minute)
    }

    fun setEndDate(year: Int, month: Int, day: Int) {
        endLocalDateTime.value = endLocalDateTime.value?.copyWithHour(year, month, day)
    }

    fun setEndTime(hour: Int, minute: Int) {
        endLocalDateTime.value = endLocalDateTime.value?.copyWithDate(hour, minute)
    }
}