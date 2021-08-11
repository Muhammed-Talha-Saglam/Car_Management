package com.example.carmanagement.viewModels

import android.content.Context
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.carmanagement.model.User
import com.example.carmanagement.model.database.CarDao
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val carDao: CarDao,
) : ViewModel() {

    val availableCars = carDao.getAvailableCars().asLiveData()

    val currentUser = MutableLiveData<User?>()
    val isLoggedIn = MutableLiveData<Boolean>(false)


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

