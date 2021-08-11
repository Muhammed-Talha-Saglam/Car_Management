package com.example.carmanagement.model.database.converters

import androidx.room.TypeConverter
import com.example.carmanagement.model.UserType

class Converters {

    @TypeConverter
    fun fromUserType(userType: UserType) : String {
        return userType.name
    }

    @TypeConverter
    fun toUserType(userType: String): UserType {
        return UserType.valueOf(userType
        )
    }

}