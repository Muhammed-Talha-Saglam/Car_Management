package com.example.carmanagement.constants

import java.time.LocalDateTime

fun LocalDateTime.copyWithDate(hour: Int, minute: Int) : LocalDateTime {
    return LocalDateTime.of(this.year, this.monthValue, this.dayOfMonth, hour, minute)

}

fun LocalDateTime.copyWithHour(year: Int, month: Int, day: Int) : LocalDateTime {
    return LocalDateTime.of(year, month, day, this.hour, this.minute)

}

fun LocalDateTime.toStringDate(): String {
    var day = this.dayOfMonth.toString()
    val month = getMonthFormat(this.monthValue)
    val year = this.year

    if (day.length == 1)
        day = "0$day"

    return "$day / $month / $year"

}

fun LocalDateTime.toStringTime(): String {

    var hour = this.hour.toString()
    var minute = this.minute.toString()

    if (minute.length == 1)
        minute = "0$minute"
    if (hour.length == 1)
        hour = "0$hour"

    return "$hour : $minute"
}

fun  LocalDateTime.toStringDateAndTime(): String {
    return this.toStringDate() + " " + this.toStringTime()
}


private fun getMonthFormat(month: Int): String {

    /*
    Transform the integer month value to string month value in device's language
     */
    return android.icu.text.DateFormatSymbols.getInstance().months[month - 1]

}
