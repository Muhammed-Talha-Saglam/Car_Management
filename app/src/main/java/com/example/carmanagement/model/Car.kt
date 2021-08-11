package com.example.carmanagement.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize



@Entity(tableName = "cars")
@Parcelize
data class Car(

    @PrimaryKey(autoGenerate = true)
    var carId: Int = 0,

    val brand: String,
    val plate: String,
    var isAvailable: Boolean = true,
    var userOwnerId: Int? = null


) : Parcelable
