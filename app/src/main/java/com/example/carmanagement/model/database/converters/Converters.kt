package com.example.carmanagement.model.database.converters

import androidx.room.TypeConverter
import com.example.carmanagement.model.Reservation
import com.example.carmanagement.model.UserType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

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
    fun fromReservationList(value: List<Reservation>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Reservation>>() {}.type
        return gson.toJson(value, type)

    }

    @TypeConverter
    fun toReservationList(value: String): List<Reservation> {
        val gson = Gson()
        val type = object : TypeToken<List<Reservation>>() {}.type
        return gson.fromJson(value, type)

    }


    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime) : String {
       return value.toString()
    }

    @TypeConverter
    fun toLocalDateTime( value: String) : LocalDateTime {
        return LocalDateTime.parse(value)
    }

}