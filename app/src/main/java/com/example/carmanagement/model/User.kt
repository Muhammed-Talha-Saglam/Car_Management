package com.example.carmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey


enum class UserType {
    STANDART,
    ADMIN
}


@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,

    val userType: UserType,
    val email: String,
    val password: String


)


