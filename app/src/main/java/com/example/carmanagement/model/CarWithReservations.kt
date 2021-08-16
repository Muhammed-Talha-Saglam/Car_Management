package com.example.carmanagement.model

import androidx.room.Embedded
import androidx.room.Relation

data class CarWithReservations(
    @Embedded val car: Car,
    @Relation(
        parentColumn = "carId",
        entityColumn = "carOwnerId"
    )
    val reservations: List<Reservation>
)
