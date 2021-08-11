package com.example.carmanagement.model.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.carmanagement.model.Car
import com.example.carmanagement.model.User

data class UserAndCar(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val car: Car
)
