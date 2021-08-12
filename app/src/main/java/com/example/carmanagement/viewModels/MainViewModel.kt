package com.example.carmanagement.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.carmanagement.model.User
import com.example.carmanagement.model.database.CarDao
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainViewModel @ViewModelInject constructor(
    private val carDao: CarDao,
) : ViewModel() {

    val availableCars = carDao.getAvailableCars().asLiveData()

    val currentUser = MutableLiveData<User?>()
    val isLoggedIn = MutableLiveData<Boolean>(false)

    val startDate = MutableLiveData<LocalDateTime>()
    val endDate = MutableLiveData<LocalDateTime>()

    fun setStartDate(year: Int, month: Int, day: Int) {
        val hour = startDate.value!!.hour
        val minute = startDate.value!!.minute

        startDate.value = LocalDateTime.of(year, month, day, hour, minute)
        Log.d("startDate", startDate.value.toString())
    }

    fun setEndDate(year: Int, month: Int, day: Int) {
        val hour = endDate.value!!.hour
        val minute = endDate.value!!.minute

        endDate.value = LocalDateTime.of(year, month, day, hour, minute)
        Log.d("endDate", endDate.value.toString())
    }

    fun setStartTime(hour: Int, minute: Int) {
        val year = startDate.value!!.year
        val month = startDate.value!!.monthValue
        val day = startDate.value!!.dayOfMonth


        startDate.value = LocalDateTime.of(year, month, day, hour, minute)
        Log.d("startDate", startDate.value.toString())

    }


    fun setEndTime(hour: Int, minute: Int) {
        val year = endDate.value!!.year
        val month = endDate.value!!.monthValue
        val day = endDate.value!!.dayOfMonth

        endDate.value = LocalDateTime.of(year, month, day, hour, minute)
        Log.d("endDate", endDate.value.toString())

    }


    fun authenticateUser(email: String, passWord: String, context: Context) {

        viewModelScope.launch {
            val shouldLogIn = carDao.authUser(email, passWord)

            if (!shouldLogIn) {
                Toast.makeText(context, "Login is unsuccessful", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(context, "Login is successful", Toast.LENGTH_LONG).show()

                val user = carDao.getUser(email, passWord)

                currentUser.value = user
                isLoggedIn.value = true

            }
        }

    }

    fun logOut() {
        currentUser.value = null
        isLoggedIn.value = false
    }


}

