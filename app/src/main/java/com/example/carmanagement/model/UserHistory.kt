package com.example.carmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "userHistory")
data class UserHistory(

    @PrimaryKey(autoGenerate = true)
    var userHistoryId: Int = 0,
    val reservationStart: LocalDateTime,
    val reservationsEnd: LocalDateTime,
    val brand: String,
    val plate: String,
    val userOwnerId: Int
)
