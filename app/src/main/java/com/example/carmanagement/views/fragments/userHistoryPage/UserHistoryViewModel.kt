package com.example.carmanagement.views.fragments.userHistoryPage

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmanagement.model.UserHistory
import com.example.carmanagement.model.database.CarDao
import kotlinx.coroutines.launch

class UserHistoryViewModel @ViewModelInject constructor(
    private val carDao: CarDao,
) : ViewModel() {

    private val _userHistory = MutableLiveData<List<UserHistory>>()
    val userHistory: LiveData<List<UserHistory>>
        get() = _userHistory


    fun getUserHistory(userId: Int) {
        viewModelScope.launch {
            _userHistory.value = carDao.getUserHistoryById(userId)
            Log.d("userHistory vm", _userHistory.value.toString())
        }
    }

    fun clearUserHistory(userId: Int) {
        viewModelScope.launch {
            carDao.clearUserHistoryByUserId(userId)
            _userHistory.value = emptyList()
        }
    }

}