package com.example.omdb.util.extensions

import com.example.omdb.domain.model.DayPart
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.Calendar

class DateTimeExtensionsTest {

    private val calendar by lazy { Calendar.getInstance() }

    @Test
    fun `test part of the day for night`() = runTest {
        calendar.set(
            2024,
            1,
            1,
            0,
            0,
            0
        )
        val part = calendar.getPartOfDay()
        assert(part == DayPart.NIGHT)
    }

    @Test
    fun `test part of the day for morning`() = runTest {
        calendar.set(
            2024,
            1,
            1,
            7,
            0,
            0
        )
        val part = calendar.getPartOfDay()
        assert(part == DayPart.MORNING)
    }

    @Test
    fun `test part of the day for afternoon`() = runTest {
        calendar.set(
            2024,
            1,
            1,
            13,
            0,
            0
        )
        val part = calendar.getPartOfDay()
        assert(part == DayPart.AFTER_NOON)
    }

    @Test
    fun `test part of the day for evening`() = runTest {
        calendar.set(
            2024,
            1,
            1,
            17,
            0,
            0
        )
        val part = calendar.getPartOfDay()
        assert(part == DayPart.EVENING)
    }
}