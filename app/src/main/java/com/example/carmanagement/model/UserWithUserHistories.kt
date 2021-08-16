package com.example.carmanagement.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithUserHistories(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val userHistories: List<UserHistory>

)
