package com.example.carmanagement.views.fragments.loginPage

import android.content.Context
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmanagement.R
import com.example.carmanagement.model.User
import com.example.carmanagement.model.database.CarDao
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val carDao: CarDao,
) : ViewModel() {

    val currentUser = MutableLiveData<User?>()
    val isLoggedIn = MutableLiveData(false)


    fun authenticateUser(email: String, passWord: String, context: Context) {

        viewModelScope.launch {

            /*
            Check database if there is a user with the given email and password
             */
            val shouldLogIn = carDao.authUser(email, passWord)

            if (!shouldLogIn) {
                Toast.makeText(context, context.getString(R.string.login_fail), Toast.LENGTH_LONG).show()
            } else {
                val user = carDao.getUser(email, passWord)
                Toast.makeText(context, context.getString(R.string.login_successful), Toast.LENGTH_LONG).show()

                currentUser.value = user
                isLoggedIn.value = true
            }
        }

    }
}