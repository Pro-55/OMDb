package com.example.omdb.util.extensions

import com.example.omdb.models.DayPart
import java.util.*

fun Calendar.getPartOfDay(): DayPart {
    val half = get(Calendar.AM_PM)
    val offset = if (half == Calendar.PM) 12 else 0
    return when (get(Calendar.HOUR) + offset) {
        in 6..11 -> DayPart.MORNING
        in 12..15 -> DayPart.AFTER_NOON
        in 16..21 -> DayPart.EVENING
        else -> DayPart.NIGHT
    }
}