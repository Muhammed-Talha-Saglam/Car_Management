package com.example.carmanagement.views.fragments.carListPage

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.carmanagement.model.Car
import com.example.carmanagement.model.Reservation
import com.example.carmanagement.model.User
import com.example.carmanagement.model.UserHistory
import com.example.carmanagement.model.database.CarDao
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CarListViewModel @ViewModelInject constructor(
    private val carDao: CarDao,
): ViewModel() {

    val allCars = carDao.getAllCars().asLiveData()

    private val _availableCars = MutableLiveData<List<Car>>()
    val availableCars: LiveData<List<Car>>
        get() = _availableCars


    val startLocalDateTime = MutableLiveData<LocalDateTime>()
    val endLocalDateTime = MutableLiveData<LocalDateTime>()

    val currentUser = MutableLiveData<User?>()


    fun getAvailableCars() {
        viewModelScope.launch {
            /*
            "Car" entity and "Reservation" entity have "one to many" relationship.
            Get each "Car" entity with respective reservations
             */
            val carsWithReservations = carDao.getCarsWithReservations()
            val carIds = mutableSetOf<Int>()

            Log.d("carsWithReservations", carsWithReservations.toString())

            /*
            List ids of the cars that whose reservation date overlaps
            with the reservation date user has selected.
             */
            carsWithReservations.forEach { carWithRez ->
                carWithRez.reservations.forEach { rez ->
                    val rezStarDate = rez.start
                    val rezEndDate = rez.end
                    if(
                        startLocalDateTime.value!!.isAfter(rezStarDate)
                        && startLocalDateTime.value!!.isBefore(rezEndDate)
                    ){
                        carIds.add(rez.carOwnerId)
                    } else if (
                        endLocalDateTime.value!!.isAfter(rezStarDate)
                        && endLocalDateTime.value!!.isBefore(rezEndDate)
                    ) {
                        carIds.add(rez.carOwnerId)
                    } else if(
                        startLocalDateTime.value!!.isBefore(rezStarDate)
                        && endLocalDateTime.value!!.isAfter(rezEndDate)
                    ) {
                        carIds.add(rez.carOwnerId)
                    } else if(
                        startLocalDateTime.value!!.isEqual(rezStarDate)
                        && endLocalDateTime.value!!.isEqual(rezEndDate)
                    ) {
                        carIds.add(rez.carOwnerId)
                    }
                }
            }

            Log.d("ids", carIds.toString())

            /*
            carIds list contains the ids of the cars that must be filtered.
             */
            _availableCars.value = carDao.getAvailableCars(carIds)
            Log.d("_availableCars", _availableCars.value.toString())
        }
    }

    fun deleteCar(car: Car) {
        viewModelScope.launch {
            carDao.deleteCar(car)
        }
    }


    fun makeNewReservation(
        brand: String,
        plate: String,
        carId: Int,
        carImage: Bitmap,
        context: Context
    ) {

        viewModelScope.launch {
            val reservation = Reservation(
                start = startLocalDateTime.value!!,
                end = endLocalDateTime.value!!,
                carOwnerId = carId
            )
            carDao.insertReservation(reservation)

            val userHistory = UserHistory(
                reservationStart = startLocalDateTime.value!!,
                reservationsEnd = endLocalDateTime.value!!,
                brand = brand,
                plate = plate,
                carImage = carImage,
                userOwnerId = currentUser.value!!.userId
            )
            carDao.insertUserHistory(userHistory)
            Toast.makeText(context, "Reservation is successful.", Toast.LENGTH_LONG).show()

            // Refresh list
            getAvailableCars()
        }

    }



}