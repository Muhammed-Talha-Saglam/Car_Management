package com.example.carmanagement.model.database.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.carmanagement.model.UserType
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {

    // UserType
    @TypeConverter
    fun fromUserType(userType: UserType) : String {
        return userType.name
    }

    @TypeConverter
    fun toUserType(userType: String): UserType {
        return UserType.valueOf(userType
        )
    }

    // Bitmap
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }


    // LocalDateTime
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime) : Long {
       return value.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toLocalDateTime( value: Long) : LocalDateTime {
        return LocalDateTime.ofEpochSecond(value,0,ZoneOffset.UTC)
    }




}