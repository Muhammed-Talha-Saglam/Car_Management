package com.example.carmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cars")
data class Car(

    @PrimaryKey(autoGenerate = true)
    var carId: Int = 0,

    val brand: String,
    val plate: String,
    val reservations: MutableList<Reservation> = mutableListOf()


)
