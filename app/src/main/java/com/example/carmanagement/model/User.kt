package com.example.carmanagement.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


enum class UserType {
    STANDART,
    ADMIN
}


@Entity(tableName = "users")
@Parcelize
data class User(

    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,

    val userType: UserType,
    val email: String,
    val password: String
): Parcelable


