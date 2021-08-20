package com.example.carmanagement.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cars")
data class Car(

    @PrimaryKey(autoGenerate = true)
    var carId: Int = 0,

    val brand: String,
    val plate: String,
    val image: Bitmap
)
