package com.example.carmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "reservations")
data class Reservation(


    @PrimaryKey(autoGenerate = true)
    var reservationId: Int = 0,

    val start: LocalDateTime,
    val end: LocalDateTime,
)
