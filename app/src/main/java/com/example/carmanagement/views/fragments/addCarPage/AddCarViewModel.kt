package com.example.carmanagement.views.fragments.addCarPage

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmanagement.model.Car
import com.example.carmanagement.model.database.CarDao
import kotlinx.coroutines.launch

class AddCarViewModel  @ViewModelInject constructor(
    private val carDao: CarDao,
) : ViewModel() {

    val carImage = MutableLiveData<Bitmap>()

    fun addNewCar(brand: String, plate: String) {
        if (brand.isNotBlank() && plate.isNotBlank()) {
            viewModelScope.launch {
                carImage.value?.let {
                    val car = Car(brand = brand, plate = plate, image = carImage.value!!)
                    carDao.insertCar(car)
                }
            }
        }
    }
}