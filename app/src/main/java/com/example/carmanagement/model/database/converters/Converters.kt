package com.example.carmanagement.model.database.converters

import androidx.room.TypeConverter
import com.example.carmanagement.model.UserType
import java.time.LocalDateTime
import java.time.ZoneOffset

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



    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime) : Long {
       return value.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toLocalDateTime( value: Long) : LocalDateTime {
        return LocalDateTime.ofEpochSecond(value,0,ZoneOffset.UTC)
    }




}